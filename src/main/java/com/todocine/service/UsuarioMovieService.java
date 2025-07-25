package com.todocine.service;

import com.todocine.dto.UsuarioMovieDTO;
import com.todocine.dto.MovieDetailDTO;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.ForbiddenException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.utils.Paginator;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public interface UsuarioMovieService {

    public Paginator<MovieDetailDTO> getUsuarioMovies(Long userId, Map<String, String> filters, String orderBy, Integer page)
            throws ForbiddenException, NotFoudException;

    public MovieDetailDTO updateUsuarioMovie(Long userId, String movieId, UsuarioMovieDTO usuarioMovieDTO)
            throws ForbiddenException, NotFoudException, BadGatewayException;
}
