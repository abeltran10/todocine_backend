package com.todocine.service;

import com.todocine.dto.UsuarioMovieDTO;
import com.todocine.dto.MovieDetailDTO;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.utils.Paginator;

public interface UsuarioMovieService {

    public Paginator<MovieDetailDTO> getUsuarioFavs(Long userId, Integer page) throws NotFoudException;

    public MovieDetailDTO updateUsuarioMovie(Long userId, String movieId, UsuarioMovieDTO usuarioMovieDTO) throws BadRequestException, NotFoudException;
}
