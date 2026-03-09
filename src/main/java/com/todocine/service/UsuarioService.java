package com.todocine.service;

import com.todocine.dto.UsuarioDTO;
import com.todocine.exceptions.ConflictException;
import com.todocine.exceptions.ForbiddenException;
import com.todocine.exceptions.NotFoudException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsuarioService extends UserDetailsService {

    UsuarioDTO insertUsuario(UsuarioDTO usuarioDTO) throws ConflictException;

    UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO) throws NotFoudException, ForbiddenException;

    UsuarioDTO getUsuarioById(Long id) throws ForbiddenException, NotFoudException;
}
