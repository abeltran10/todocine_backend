package com.todocine.service;

import com.todocine.dto.request.UsuarioMovieDTO;
import com.todocine.dto.response.MovieDetailDTO;
import com.todocine.dto.response.Paginator;

import java.util.Map;

public interface UsuarioMovieService {

    public Paginator<MovieDetailDTO> getUsuarioMovies(String username, Map<String, String> filters, String orderBy, Integer page);

    public MovieDetailDTO updateUsuarioMovie(String username, Long movieId, UsuarioMovieDTO usuarioMovieDTO);

}
