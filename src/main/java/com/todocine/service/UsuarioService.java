package com.todocine.service;

import com.todocine.dto.UsuarioDTO;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.dto.MovieDTO;
import com.todocine.utils.Paginator;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService extends UserDetailsService {

    UsuarioDTO getUsuarioById(String id) throws NotFoudException;

    UsuarioDTO getUsuarioByName (String username) throws NotFoudException;

    UsuarioDTO insertUsuario(UsuarioDTO usuarioDTO) throws BadRequestException;

    UsuarioDTO updateUsuario(String id, UsuarioDTO usuarioDTO) throws NotFoudException;

    Paginator getUsuarioFavs(String username, Integer page) throws NotFoudException;

    MovieDTO addFavoritosByUserId(String id, MovieDTO movieDTO) throws BadRequestException, NotFoudException;

    void deleteFavoritosByUserId(String id, String movieId) throws BadRequestException, NotFoudException;
}
