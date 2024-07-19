package com.todocine.service;

import com.todocine.dto.FavoritosDTO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.dto.MovieDTO;
import com.todocine.utils.Paginator;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService extends UserDetailsService {

    UsuarioDTO getUsuarioById(Long id) throws NotFoudException;

    UsuarioDTO getUsuarioByName (String username) throws NotFoudException;

    UsuarioDTO insertUsuario(UsuarioDTO usuarioDTO) throws BadRequestException;

    UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO) throws NotFoudException;

    Paginator getUsuarioFavs(Long id, Integer page) throws NotFoudException;

    FavoritosDTO addFavoritosByUserId(Long id, MovieDTO movieDTO) throws BadRequestException, NotFoudException;

    void deleteFavoritosByUserId(Long id, String movieId) throws BadRequestException, NotFoudException;
}
