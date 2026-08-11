package com.todocine.service;

import com.todocine.dto.response.MovieDTO;
import com.todocine.dto.response.Paginator;

import java.io.IOException;

public interface TMDBService {

    MovieDTO getMovieById(Long id) throws IOException;

    Paginator<MovieDTO> getMoviesByName(String name, Integer pagina) throws IOException;

    Paginator<MovieDTO> getMoviesPlayingNow(String country, Integer pagina) throws IOException;

}
