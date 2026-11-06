package com.todocine.service;

import com.todocine.dto.request.UsuarioReqDTO;
import com.todocine.dto.response.UsuarioDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService extends UserDetailsService {

    UsuarioDTO insertUsuario(UsuarioReqDTO usuarioReqDTO);

    UsuarioDTO updateUsuario(String username, UsuarioReqDTO usuarioReqDTO);

    UsuarioDTO getUsuarioById(String username);
}
