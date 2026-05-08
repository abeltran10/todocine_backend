package com.todocine.service;

import com.todocine.dto.response.MovieDetailDTO;
import com.todocine.utils.Paginator;

import java.util.Map;

public interface MovieService {

    MovieDetailDTO getMovieDetailById(Long id);

    Paginator getMovies(Map<String, String> filters, Integer pagina);

}
