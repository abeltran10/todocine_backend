package com.todocine.service;

import com.todocine.exceptions.BadGateWayException;
import com.todocine.model.MoviePage;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface MovieService {

    public MoviePage getMovieByName(String name, Integer pagina) throws BadGateWayException;
}
