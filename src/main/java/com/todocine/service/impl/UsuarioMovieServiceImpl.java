package com.todocine.service.impl;

import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioMovieDAO;
import com.todocine.dao.UsuarioMovieRepo;
import com.todocine.dto.request.UsuarioMovieDTO;
import com.todocine.dto.response.MovieDTO;
import com.todocine.dto.response.MovieDetailDTO;
import com.todocine.entities.Movie;
import com.todocine.entities.UserMovieId;
import com.todocine.entities.Usuario;
import com.todocine.entities.UsuarioMovie;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.ForbiddenException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.TMDBService;
import com.todocine.service.UsuarioMovieService;
import com.todocine.utils.Paginator;
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

import static com.todocine.configuration.Constants.*;

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
    private UsuarioMovieRepo usuarioMovieRepo;

    @Override
    @Transactional(readOnly = true)
    public Paginator<MovieDetailDTO> getUsuarioMovies(Long userId, Map<String,String> filters, String orderBy, Integer page) {
        int pagina = page - 1;
        Paginator<MovieDetailDTO> paginator = new Paginator<>();
        Paginator<UsuarioMovie> usuarioMoviePaginator = new Paginator<>();
        List<MovieDetailDTO> movieDetailDTOS = new ArrayList<>();

        if (getCurrentUserId().equals(userId)) {

            usuarioMoviePaginator = usuarioMovieRepo.getUserMoviesByFilter(userId, filters, orderBy,21, (pagina) * 21);

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
                paginator.setPage(page);
                paginator.setResults(movieDetailDTOS);
            }

            return paginator;

        } else {
            throw new ForbiddenException(USER_FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public MovieDetailDTO updateUsuarioMovie(Long userId, Long movieId, UsuarioMovieDTO usuarioMovieDTO) {
        if (getCurrentUserId().equals(userId)) {
            Movie movie = null;
            MovieDTO movieDTO = null;

             try {
                 Map<String, Object> movieMap = tmdbService.getMovieById(usuarioMovieDTO.getMovieId());
                 if (movieMap.get("id") == null)
                     throw new NotFoudException(MOVIE_NOTFOUND);
                 else {
                     movie = movieDAO.findById(movieId).orElse(null);
                     movieDTO = MovieMapper.toDTO(movieMap);

                     if (movie == null) {
                        movie = MovieMapper.toEntity(movieDTO);
                        movieDAO.save(movie);
                     }
                 }

                 UserMovieId userMovieId = new UserMovieId(new Usuario(userId), movie);
                 UsuarioMovie usuarioMovie = usuarioMovieDAO.findById(userMovieId).orElse(null);

                 if (usuarioMovie != null && usuarioMovie.getVoto() != null &&
                        (usuarioMovieDTO.getVoto() != null && !usuarioMovieDTO.getVoto().equals(0.0D)))
                    actualizarMediaVotos(movie, usuarioMovieDTO.getVoto(), usuarioMovie.getVoto());
                 else if (usuarioMovieDTO.getVoto() != null && !usuarioMovieDTO.getVoto().equals(0.0D))
                    calcularMediaVotos(movie, usuarioMovieDTO.getVoto());

                 if (usuarioMovie == null)
                     usuarioMovie = UsuarioMovieMapper.toEntity(usuarioMovieDTO);
                 else {
                     usuarioMovie.setFavoritos(usuarioMovieDTO.getFavoritos() ? "S" : "N");
                     usuarioMovie.setVista(usuarioMovieDTO.getVista() ? "S" : "N");
                     usuarioMovie.setVoto(usuarioMovieDTO.getVoto() != null ? usuarioMovieDTO.getVoto() : usuarioMovie.getVoto());
                 }

                 movieDAO.save(movie);

                 usuarioMovieDAO.save(usuarioMovie);

                 movieDTO.setVotosMediaTC(movie.getVotosMediaTC());
                 movieDTO.setTotalVotosTC(movie.getTotalVotosTC());

                 return new MovieDetailDTO(movieDTO, usuarioMovieDTO.getFavoritos(), usuarioMovie.getVoto(), usuarioMovieDTO.getVista());

            } catch (IOException ex) {
                throw new BadGatewayException(TMDB_ERROR);
            }
        } else {
            throw new ForbiddenException(USER_FORBIDDEN);
        }

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

    /*@Override
    @Transactional
    public void deleteUsuarioMovie(Long userId, Long movieId) throws ForbiddenException, NotFoudException {
        if (getCurrentUserId().equals(userId)) {
            Usuario u = new Usuario(userId);
            Movie m = new Movie(movieId);

            UsuarioMovie usuarioMovie = usuarioMovieDAO.findById(new UserMovieId(u, m)).orElse(null);

            if (usuarioMovie != null) {
                usuarioMovieDAO.delete(usuarioMovie);
            } else {
                throw new NotFoudException(FAVORITOS_NOTFOUND);
            }
        } else {
            throw new ForbiddenException(USER_FORBIDDEN);
        }
    }*/

}
