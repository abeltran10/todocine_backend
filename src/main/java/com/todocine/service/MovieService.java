package com.todocine.service;

import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.VotoDTO;
import com.todocine.utils.Paginator;

public interface MovieService {

    MovieDTO getMovieById(String id) throws NotFoudException, BadGatewayException;

    Paginator getMovieByName(String name, Integer pagina) throws NotFoudException, BadGatewayException;

    Paginator getMoviesPlayingNow(String country, Integer pagina) throws NotFoudException, BadGatewayException;

    MovieDTO updateVote(String movieId, Long usuarioId, VotoDTO votoDTO) throws BadGatewayException, NotFoudException;
}
