package com.todocine.service.impl;


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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    private TMDBService tmdbService;


    @Override
    public Movie getMovieById(String id) throws  ResponseStatusException {
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

}
