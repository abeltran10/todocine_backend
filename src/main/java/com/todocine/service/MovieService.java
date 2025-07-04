package com.todocine.service;

import com.todocine.dto.MovieDTO;
import com.todocine.dto.MovieDetailDTO;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.utils.Paginator;

public interface MovieService {

    MovieDetailDTO getMovieDetailById(String id) throws NotFoudException, BadGatewayException;

    Paginator getMovieByName(String name, Integer pagina) throws NotFoudException, BadGatewayException;

    Paginator getMoviesPlayingNow(String country, Integer pagina) throws NotFoudException, BadGatewayException;

}
