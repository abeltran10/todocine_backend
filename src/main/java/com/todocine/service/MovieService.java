package com.todocine.service;

import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.model.Movie;
import com.todocine.model.Voto;
import com.todocine.utils.Paginator;

public interface MovieService {

    Movie getMovieById(String id) throws NotFoudException, BadGatewayException;

    Paginator getMovieByName(String name, Integer pagina) throws NotFoudException, BadGatewayException;

    Paginator getMoviesPlayingNow(String country, Integer pagina) throws NotFoudException, BadGatewayException;

    Movie addVote(String id, Voto voto) throws BadGatewayException;

    Movie updateVote(String movieId, String votoId, Voto voto) throws BadGatewayException, NotFoudException, BadRequestException;
}
