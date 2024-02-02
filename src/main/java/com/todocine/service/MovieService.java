package com.todocine.service;

import com.todocine.model.Movie;
import com.todocine.model.Voto;
import com.todocine.utils.Paginator;
import org.springframework.web.server.ResponseStatusException;

public interface MovieService {

    Movie getMovieById(String id) throws ResponseStatusException;

    Paginator getMovieByName(String name, Integer pagina) throws ResponseStatusException;

    Paginator getMoviesPlayingNow(String country, Integer pagina) throws ResponseStatusException;

    Movie addVote(String id, Voto voto) throws ResponseStatusException;

    Movie updateVote(String movieId, String votoId, Voto voto) throws ResponseStatusException;
}
