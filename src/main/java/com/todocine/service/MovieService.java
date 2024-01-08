package com.todocine.service;

import com.todocine.model.Movie;
import com.todocine.model.Paginator;
import com.todocine.model.Video;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    Movie getMovieById(String id) throws ResponseStatusException;

    Paginator getMovieByName(String name, Integer pagina) throws ResponseStatusException;

    Paginator getMoviesPlayingNow(String country, Integer pagina) throws ResponseStatusException;

}
