package com.todocine.service.impl;

import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioMovieDAO;
import com.todocine.dto.request.UsuarioMovieDTO;
import com.todocine.dto.response.MovieDTO;
import com.todocine.dto.response.MovieDetailDTO;
import com.todocine.dto.response.Paginator;
import com.todocine.entities.Movie;
import com.todocine.entities.UserMovieId;
import com.todocine.entities.Usuario;
import com.todocine.entities.UsuarioMovie;
import com.todocine.enums.TipoActividad;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.ForbiddenException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.ActividadService;
import com.todocine.service.TMDBService;
import com.todocine.service.UsuarioMovieService;
import com.todocine.utils.mapper.MovieMapper;
import com.todocine.utils.mapper.UsuarioMovieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.todocine.utils.Constants.*;

@Service
public class UsuarioMovieServiceImpl extends BaseServiceImpl implements UsuarioMovieService {
    Logger log = LoggerFactory.getLogger(UsuarioMovieServiceImpl.class);

    @Autowired
    private TMDBService tmdbService;

    @Autowired
    private UsuarioMovieDAO usuarioMovieDAO;

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    ActividadService actividadService;

    @Override
    @Transactional(readOnly = true)
    public Paginator<MovieDetailDTO> getUsuarioMovies(String username, Map<String,String> filters, String orderBy, Integer page) {
        Paginator<MovieDetailDTO> paginator = new Paginator<>();
        Paginator<UsuarioMovie> usuarioMoviePaginator = new Paginator<>();
        List<MovieDetailDTO> movieDetailDTOS = new ArrayList<>();

        if (getCurrentUsername().equals(username)) {

            usuarioMoviePaginator = usuarioMovieDAO.getUserMoviesByFilter(username, filters, orderBy, FAVOURITES_PAGE_SIZE, page);

            if (!usuarioMoviePaginator.getResults().isEmpty()) {
                movieDetailDTOS = usuarioMoviePaginator.getResults().stream()
                        .map(usuarioMovie -> {
                            Movie movie = usuarioMovie.getId().getMovie();

                            MovieDetailDTO movieDetailDTO = new MovieDetailDTO(MovieMapper.toDTO(movie),
                                    true, usuarioMovie.getVoto(), usuarioMovie.getVista().equals("S"));

                            return movieDetailDTO;
                        }).toList();
            }

            if (!movieDetailDTOS.isEmpty()) {
                paginator.setTotalPages(usuarioMoviePaginator.getTotalPages());
                paginator.setTotalResults(usuarioMoviePaginator.getTotalResults());
                paginator.setPage(usuarioMoviePaginator.getPage());
                paginator.setResults(movieDetailDTOS);
            }

            return paginator;

        } else {
            throw new ForbiddenException(USER_FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public MovieDetailDTO updateUsuarioMovie(String username, Long movieId, UsuarioMovieDTO usuarioMovieDTO) {
        if (!username.equals(usuarioMovieDTO.getUsername()) || !movieId.equals(usuarioMovieDTO.getMovieId())) {
            throw new BadRequestException(ID_NOT_MATCH);
        }

        if (!getCurrentUsername().equals(username)) {
            throw new ForbiddenException(USER_FORBIDDEN);
        }

        Usuario usuario = new Usuario(username);

        try {
            MovieDTO movieDTO = fetchMovieDTO(movieId);
            Movie movie = getOrCreateMovie(movieId, movieDTO);

            UserMovieId userMovieId = new UserMovieId(usuario, movie);
            UsuarioMovie usuarioMovie = usuarioMovieDAO.findById(userMovieId).orElse(null);

            boolean votoModificado = updateRatingIfNeeded(movie, usuarioMovie, usuarioMovieDTO.getVoto());
            if (votoModificado) {
                movieDAO.save(movie);
            }

            TipoActividad tipoActividad = resolveTipoActividad(usuarioMovie, usuarioMovieDTO);
            usuarioMovie = upsertUsuarioMovie(usuarioMovie, usuarioMovieDTO);
            usuarioMovieDAO.save(usuarioMovie);

            movieDTO.setVotosMediaTC(movie.getVotosMediaTC());
            movieDTO.setTotalVotosTC(movie.getTotalVotosTC());

            String detalleExtra = buildDetalleExtra(tipoActividad, usuarioMovieDTO);

            actividadService.guardarActividad(
                    usuario,
                    tipoActividad,
                    movie.getId(),
                    movie.getTitle(),
                    movie.getPosterPath(),
                    detalleExtra
            );

            return new MovieDetailDTO(
                    movieDTO,
                    "S".equals(usuarioMovie.getFavoritos()),
                    usuarioMovie.getVoto(),
                    "S".equals(usuarioMovie.getVista())
            );
        } catch (IOException ex) {
            throw new BadGatewayException(TMDB_ERROR);
        }
    }

    private MovieDTO fetchMovieDTO(Long movieId) throws IOException {
        MovieDTO movieDTO = tmdbService.getMovieById(movieId);
        if (movieDTO == null) {
            throw new NotFoudException(MOVIE_NOTFOUND);
        }
        return movieDTO;
    }

    private Movie getOrCreateMovie(Long movieId, MovieDTO movieDTO) {
        return movieDAO.findById(movieId)
                .orElseGet(() -> {
                    Movie nuevaMovie = MovieMapper.toEntity(movieDTO);
                    return movieDAO.save(nuevaMovie);
                });
    }

    private boolean updateRatingIfNeeded(Movie movie, UsuarioMovie existing, Double nuevoVoto) {
        if (nuevoVoto == null || nuevoVoto.equals(0.0D)) {
            return false;
        }
        if (existing != null && existing.getVoto() != null) {
            actualizarMediaVotos(movie, nuevoVoto, existing.getVoto());
        } else {
            calcularMediaVotos(movie, nuevoVoto);
        }
        return true;
    }

    private TipoActividad resolveTipoActividad(UsuarioMovie existing, UsuarioMovieDTO dto) {
        boolean favDto = dto.getFavoritos();
        boolean vistaDto = dto.getVista();

        if (existing == null) {
            if (favDto) return TipoActividad.FAVORITO_PELICULA;
            if (vistaDto) return TipoActividad.VISTA_PELICULA;
            return TipoActividad.PUNTUACION_PELICULA;
        }

        boolean prevFav = "S".equals(existing.getFavoritos());
        boolean prevVista = "S".equals(existing.getVista());

        if (favDto != prevFav) return TipoActividad.FAVORITO_PELICULA;
        if (vistaDto != prevVista) return TipoActividad.VISTA_PELICULA;
        return TipoActividad.PUNTUACION_PELICULA;
    }

    private UsuarioMovie upsertUsuarioMovie(UsuarioMovie existing, UsuarioMovieDTO dto) {
        if (existing == null) {
            return UsuarioMovieMapper.toEntity(dto);
        }
        existing.setFavoritos(dto.getFavoritos() ? "S" : "N");
        existing.setVista(dto.getVista() ? "S" : "N");
        if (dto.getVoto() != null) {
            existing.setVoto(dto.getVoto());
        }
        return existing;
    }

    private String buildDetalleExtra(TipoActividad tipoActividad, UsuarioMovieDTO dto) {
        return switch (tipoActividad) {
            case FAVORITO_PELICULA -> dto.getFavoritos()
                    ? "Película añadida a favoritos"
                    : "Película eliminada de favoritos";
            case VISTA_PELICULA -> dto.getVista()
                    ? "Película vista"
                    : "Película no vista";
            case PUNTUACION_PELICULA -> "Puntuación " + dto.getVoto() + "/5";
            default -> "";
        };
    }

    private void actualizarMediaVotos(Movie movieEntity, Double newVote, Double oldVoto) {
        Double total = movieEntity.getVotosMediaTC() * movieEntity.getTotalVotosTC();
        Double totalOld = total - oldVoto;

        Double votosMedia = Math.round(((totalOld + newVote) / movieEntity.getTotalVotosTC()) * 10.0) / 10.0;
        movieEntity.setVotosMediaTC(votosMedia);
    }

    private void calcularMediaVotos(Movie movieEntity, Double voto) {
        Integer totalVotosTC = movieEntity.getTotalVotosTC();
        Double total = movieEntity.getVotosMediaTC() * totalVotosTC;

        ++totalVotosTC;
        movieEntity.setTotalVotosTC(totalVotosTC);
        Double votosMedia = Math.round(((total + voto) / totalVotosTC) * 10.0) / 10.0;
        movieEntity.setVotosMediaTC(votosMedia);
    }

}
