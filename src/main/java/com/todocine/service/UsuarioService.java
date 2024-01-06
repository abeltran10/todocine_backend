package com.todocine.service;

import com.todocine.model.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService extends UserDetailsService {

    Usuario getUsuarioById(String id);

    Usuario getUsuarioByName (String username);

    Usuario insertUsuario(Usuario usuario);
}
