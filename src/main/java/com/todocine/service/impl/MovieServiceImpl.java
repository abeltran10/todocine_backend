package com.todocine.service.impl;


import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.model.Movie;
import com.todocine.model.Paginator;
import com.todocine.model.Video;
import com.todocine.service.MovieService;
import com.todocine.service.TMDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    private TMDBService tmdbService;

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;


    @Override
    public Movie getMovieById(String id) throws ResponseStatusException {
        try {
            Movie movie = new Movie(tmdbService.getMovieById(id));

            logger.info(movie.toString());

            if (movie.getId() == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la película");
            } else {
                return movie;
            }
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "La respuesta de TMDB ha fallado");
        }
    }

    @Override
    public Paginator getMovieByName(String name, Integer pagina) throws ResponseStatusException {
        try {

            Map<String, Object> map = tmdbService.getMoviesByName(name, pagina);

            Paginator<Movie> moviePage = new Paginator<>(map);
            List<Movie> results = ((List<Map<String, Object>>) map.get("results")).stream()
                    .map(item -> new Movie(item)).collect(Collectors.toList());
            moviePage.setResults(results);

            logger.info(moviePage.toString());

            if (moviePage == null || moviePage.getResults() == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la película con ese nombre");
            } else
                return moviePage;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "La respuesta de TMDB ha fallado");
        }

    }

    @Override
    public Paginator getMoviesPlayingNow(String country, Integer pagina) throws ResponseStatusException {
        try {

            Map<String, Object> map = tmdbService.getMoviesPlayingNow(country, pagina);

            Paginator<Movie> moviePage = new Paginator<>(map);
            List<Movie> results = ((List<Map<String, Object>>) map.get("results")).stream()
                    .map(item -> new Movie(item)).collect(Collectors.toList());
            moviePage.setResults(results);

            logger.info(moviePage.toString());

            if (moviePage == null || moviePage.getResults() == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la cartelera para esa región");
            } else
                return moviePage;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "La respuesta de TMDB ha fallado");
        }
    }

    @Override
    public Paginator getFavsByUsername(String usuarioId) throws ResponseStatusException {
        Paginator<Movie> paginator = new Paginator<>();
        List<Movie> movieList = new ArrayList<>();
        List<MovieDTO> movieDTOS = usuarioDAO.findById(usuarioId).get().getFavoritos();

        logger.info(movieDTOS.toString());

        if (movieDTOS != null && !movieDTOS.isEmpty()) {
            movieList = movieDTOS.stream().map(movieDTO -> new Movie(movieDTO)).collect(Collectors.toList());

            paginator.setPage(1);
            paginator.setResults(movieList);

            int total = movieList.size()/21;

            paginator.setTotalPages(total + 1);
            paginator.setTotalResults(movieList.size());

            return paginator;

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay favoritos para el usuario");
        }

    }

}
