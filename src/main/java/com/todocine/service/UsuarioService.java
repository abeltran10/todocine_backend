package com.todocine.service;

import com.todocine.model.Movie;
import com.todocine.model.Usuario;
import com.todocine.utils.Paginator;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.server.ResponseStatusException;

public interface UsuarioService extends UserDetailsService {

    Usuario getUsuarioById(String id);

    Usuario getUsuarioByName (String username);

    Usuario insertUsuario(Usuario usuario) throws ResponseStatusException;

    Usuario updateUsuario(String id, Usuario usuario);

    Paginator getUsuarioFavs(String username, Integer page);

    Usuario addFavoritosByUserId(String id, Movie movie) throws ResponseStatusException;

    void deleteFavoritosByUserId(String id, String movieId) throws ResponseStatusException;
}
