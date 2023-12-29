package com.todocine.service.impl;

import com.todocine.dao.MovieDAO;
import com.todocine.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MovieServiceImpl implements MovieService {

    Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);
    @Autowired
    private MovieDAO movieDAO;

    public ResponseEntity getMovieByName(String name) {
        ResponseEntity responseEntity = null;
        try {
           responseEntity =  movieDAO.searchMovie(name);

            logger.info(responseEntity.getBody().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return responseEntity;
    }

}
