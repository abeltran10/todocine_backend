package com.todocine.service;

import com.todocine.dto.MovieDetailDTO;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.utils.Paginator;

import java.util.Map;

public interface MovieService {

    MovieDetailDTO getMovieDetailById(String id) throws NotFoudException, BadGatewayException;

    Paginator getMovies(Map<String, String> filters, Integer pagina) throws NotFoudException, BadRequestException, BadGatewayException;

}
