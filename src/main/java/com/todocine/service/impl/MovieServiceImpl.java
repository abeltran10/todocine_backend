package com.todocine.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todocine.dao.MovieDAO;
import com.todocine.exceptions.BadGateWayException;
import com.todocine.model.Movie;
import com.todocine.model.MoviePage;
import com.todocine.service.MovieService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService {

    Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    private MovieDAO movieDAO;


    @Override
   public MoviePage getMovieByName(String name, Integer pagina) throws BadGateWayException {
       ObjectMapper objectMapper = new ObjectMapper();
        String body = null;
        MoviePage moviePage = null;

        try {

            body = movieDAO.getMoviesByName(name, pagina);

            logger.info(body);

            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            moviePage = objectMapper.readValue(body, MoviePage.class);

            logger.info(moviePage.toString());

       } catch (IOException e) {
            throw new BadGateWayException(e.getMessage());
       } finally {
           return moviePage;
       }

    }

}
