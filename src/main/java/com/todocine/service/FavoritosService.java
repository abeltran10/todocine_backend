package com.todocine.service;

import com.todocine.dto.MovieDTO;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.utils.Paginator;

public interface FavoritosService {

    public Paginator<MovieDTO> getUsuarioFavs(Integer page) throws NotFoudException;

    public MovieDTO addFavoritos(MovieDTO movieDTO) throws BadRequestException, NotFoudException;

    public void deleteFavoritos(String movieId) throws BadRequestException, NotFoudException;
}
