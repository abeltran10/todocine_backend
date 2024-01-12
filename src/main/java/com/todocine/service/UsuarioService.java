package com.todocine.service;

import com.todocine.model.Movie;
import com.todocine.model.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.server.ResponseStatusException;

public interface UsuarioService extends UserDetailsService {

    Usuario getUsuarioById(String id);

    Usuario getUsuarioByName (String username);

    Usuario insertUsuario(Usuario usuario);

    Usuario updateUsuario(String id, Usuario usuario);

    Usuario addFavoritosByUserId(String id, String movieId) throws ResponseStatusException;

    void deleteFavoritosByUserId(String id, String movieId) throws ResponseStatusException;
}
