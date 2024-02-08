package com.todocine.service;

import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.model.Movie;
import com.todocine.model.Usuario;
import com.todocine.utils.Paginator;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService extends UserDetailsService {

    Usuario getUsuarioById(String id) throws NotFoudException;

    Usuario getUsuarioByName (String username) throws NotFoudException;

    Usuario insertUsuario(Usuario usuario) throws BadRequestException;

    Usuario updateUsuario(String id, Usuario usuario) throws NotFoudException;

    Paginator getUsuarioFavs(String username, Integer page) throws NotFoudException;

    Movie addFavoritosByUserId(String id, Movie movie) throws BadRequestException, NotFoudException;

    void deleteFavoritosByUserId(String id, String movieId) throws BadRequestException, NotFoudException;
}
