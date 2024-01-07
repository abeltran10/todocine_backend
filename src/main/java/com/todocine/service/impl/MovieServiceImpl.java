package com.todocine.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@Service
public class MovieServiceImpl implements MovieService {

    Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    private TMDBService tmdbService;


    @Override
    public Movie getMovieById(String id) throws  ResponseStatusException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        Movie movie = null;

       try {
           String body = tmdbService.getMovieById(id);

           logger.info(body);

           movie = objectMapper.readValue(body, Movie.class);

           logger.info(movie.toString());

           if (movie.getId() == null || movie.getId().equals("null")) {
               logger.info("entra exception");
               throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la película");
           } else {
               List<Video> videos = new ArrayList<>();
               movie.setVideos(videos);
               movie.getVideos().addAll(getVideosByMovieId(id));
               return movie;
           }
       } catch (IOException ex) {
           throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "La respuesta de TMDB ha fallado");
       }
    }

    @Override
   public Paginator getMovieByName(String name, Integer pagina) throws ResponseStatusException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        Paginator<Movie> moviePage = null;

        try {

            String body = tmdbService.getMoviesByName(name, pagina);

            logger.info(body);


            moviePage = objectMapper.readValue(body, Paginator.class);

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
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        Paginator<Movie> moviePage = null;

        try {

            String body = tmdbService.getMoviesPlayingNow(country, pagina);

            logger.info(body);

            moviePage = objectMapper.readValue(body, Paginator.class);

            logger.info(moviePage.toString());
            if (moviePage == null || moviePage.getPage().equals("null")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la cartelera para esa región");
            } else
                return moviePage;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "La respuesta de TMDB ha fallado");
        }
    }

    public List<Video> getVideosByMovieId(String id) throws ResponseStatusException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        List<Video> videos = new ArrayList<>();

        try {
            String body = tmdbService.getVideoByMovieId(id);

            logger.info(body);

            Map<String,Object> map = objectMapper.readValue(body, Map.class);

            if (map == null || map.get("id") == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se han encontrado videos");
            } else {
                videos.addAll((List<Video>) map.get("results"));

                logger.info(videos.toString());
            }

            return videos;
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "La respuesta de TMDB ha fallado");
        }
    }

}
