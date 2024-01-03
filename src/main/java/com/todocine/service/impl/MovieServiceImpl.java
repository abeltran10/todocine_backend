package com.todocine.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todocine.dao.MovieDAO;
import com.todocine.exceptions.BadGateWayException;
import com.todocine.model.Movie;
import com.todocine.model.MoviePage;
import com.todocine.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MovieServiceImpl implements MovieService {

    Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    private MovieDAO movieDAO;


    @Override
    public Movie getMovieById(String id) throws BadGateWayException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        Movie movie = null;

        try {
            String body = movieDAO.getMovieById(id);

            logger.info(body);

            movie = objectMapper.readValue(body, Movie.class);
        } catch (IOException ex) {
            throw new BadGateWayException(ex.getMessage());
        } finally {
            return movie;
        }
    }

    @Override
   public MoviePage getMovieByName(String name, Integer pagina) throws BadGateWayException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        MoviePage moviePage = null;

        try {

            String body = movieDAO.getMoviesByName(name, pagina);

            logger.info(body);


            moviePage = objectMapper.readValue(body, MoviePage.class);

            logger.info(moviePage.toString());

       } catch (IOException e) {
            throw new BadGateWayException(e.getMessage());
       } finally {
           return moviePage;
       }

    }

}
