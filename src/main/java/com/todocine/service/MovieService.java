package com.todocine.service;

import com.todocine.dto.response.MovieDetailDTO;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.utils.Paginator;

import java.util.Map;

public interface MovieService {

    MovieDetailDTO getMovieDetailById(Long id);

    Paginator getMovies(Map<String, String> filters, Integer pagina);

}
