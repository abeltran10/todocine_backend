package com.todocine.service;

import com.todocine.model.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService extends UserDetailsService {

    public Usuario getUsuarioById(String id);

    Usuario insertUsuario(Usuario usuario);
}
