package com.todocine.service;

import com.todocine.model.Movie;
import com.todocine.model.Paginator;
import org.springframework.web.server.ResponseStatusException;

public interface MovieService {

    Movie getMovieById(String id) throws ResponseStatusException;

    Paginator getMovieByName(String name, Integer pagina) throws ResponseStatusException;

    Paginator getMoviesPlayingNow(String country, Integer pagina) throws ResponseStatusException;

    Paginator getFavsByUsername(String username, Integer page);
}
