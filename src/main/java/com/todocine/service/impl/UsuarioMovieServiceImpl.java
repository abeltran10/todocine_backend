package com.todocine.service.impl;

import com.todocine.dao.UsuarioMovieDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dao.UsuarioMovieRepo;
import com.todocine.dto.UsuarioMovieDTO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.MovieDetailDTO;
import com.todocine.entities.*;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.UsuarioMovieService;
import com.todocine.service.TMDBService;
import com.todocine.utils.Paginator;
import com.todocine.utils.mapper.MovieMapper;
import com.todocine.utils.mapper.UsuarioMovieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UsuarioMovieServiceImpl extends BaseServiceImpl implements UsuarioMovieService {
    Logger log = LoggerFactory.getLogger(UsuarioMovieServiceImpl.class);

    @Autowired
    private TMDBService tmdbService;

    @Autowired
    private UsuarioMovieDAO usuarioMovieDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private UsuarioMovieRepo usuarioMovieRepo;

    @Override
    @Transactional(readOnly = true)
    public Paginator<MovieDetailDTO> getUsuarioMovies(Long userId, Map<String,String> filters, Integer page) throws BadRequestException, NotFoudException {
        int pagina = page - 1;
        Paginator<MovieDetailDTO> paginator = new Paginator<>();
        Paginator<UsuarioMovie> usuarioMoviePaginator = new Paginator<>();
        List<MovieDetailDTO> movieDetailDTOS = new ArrayList<>();

        if (getCurrentUserId().equals(userId)) {

            usuarioMoviePaginator = usuarioMovieRepo.getUserMoviesByFilter(userId, filters, 21, (pagina) * 21);

            if (!usuarioMoviePaginator.getResults().isEmpty()) {
                movieDetailDTOS = usuarioMoviePaginator.getResults().stream()
                        .map(usuarioMovie -> {
                            Movie movie = usuarioMovie.getId().getMovie();

                            MovieDetailDTO movieDetailDTO = new MovieDetailDTO(MovieMapper.toDTO(movie),
                                    true, usuarioMovie.getVoto(), usuarioMovie.getVista().equals("S"));

                            return movieDetailDTO;
                        }).toList();
            }

            if (movieDetailDTOS.isEmpty())
                throw new NotFoudException("No hay favoritos para el usuario");

            paginator.setTotalPages(usuarioMoviePaginator.getTotalPages());
            paginator.setTotalResults(usuarioMoviePaginator.getTotalResults());
            paginator.setPage(page);
            paginator.setResults(movieDetailDTOS);

            return paginator;

        } else {
            throw new BadRequestException("El usuario no es el de la sesión");
        }
    }

    @Override
    @Transactional
    public MovieDetailDTO updateUsuarioMovie(Long userId, String movieId, UsuarioMovieDTO usuarioMovieDTO) throws BadRequestException, NotFoudException {
        if (getCurrentUserId().equals(userId)) {
            Movie movie = null;
            MovieDTO movieDTO = null;

             try {
                 Map<String, Object> movieMap = tmdbService.getMovieById(usuarioMovieDTO.getMovieId());
                 if (movieMap.get("id") == null)
                     throw new NotFoudException("No existe la película");
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

                 usuarioMovie = UsuarioMovieMapper.toEntity(usuarioMovieDTO);

                 movieDAO.save(movie);

                 usuarioMovieDAO.save(usuarioMovie);

                 movieDTO.setVotosMediaTC(movie.getVotosMediaTC());
                 movieDTO.setTotalVotosTC(movie.getTotalVotosTC());

                 return new MovieDetailDTO(movieDTO, usuarioMovieDTO.getFavoritos(), usuarioMovieDTO.getVoto(), usuarioMovieDTO.getVista());

            } catch (IOException ex) {
                throw new NotFoudException("No existe la película");
            }
        } else {
            throw new BadRequestException("El usuario no es el de la sesión");
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

}
