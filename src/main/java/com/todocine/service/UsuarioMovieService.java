package com.todocine.service;

import com.todocine.dto.request.UsuarioMovieDTO;
import com.todocine.dto.response.MovieDetailDTO;
import com.todocine.utils.Paginator;

import java.util.Map;

public interface UsuarioMovieService {

    public Paginator<MovieDetailDTO> getUsuarioMovies(Long userId, Map<String, String> filters, String orderBy, Integer page);

    public MovieDetailDTO updateUsuarioMovie(Long userId, Long movieId, UsuarioMovieDTO usuarioMovieDTO);

    //public void deleteUsuarioMovie(Long userId, Long movieId);
}
