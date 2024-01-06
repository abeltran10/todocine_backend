package com.todocine.service;

import com.todocine.model.Movie;
import com.todocine.model.MoviePage;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

public interface MovieService {

    Movie getMovieById(String id) throws ResponseStatusException;

    public MoviePage getMovieByName(String name, Integer pagina) throws ResponseStatusException;

    MoviePage getMoviesPlayingNow(String country, Integer pagina) throws ResponseStatusException;

}
