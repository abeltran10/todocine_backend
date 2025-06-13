package com.todocine.service;

import com.todocine.dto.UsuarioDTO;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.ForbiddenException;
import com.todocine.exceptions.NotFoudException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService extends UserDetailsService {

    UsuarioDTO getUsuarioByName (String username) throws NotFoudException, BadRequestException;

    UsuarioDTO insertUsuario(UsuarioDTO usuarioDTO) throws BadRequestException;

    UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO) throws NotFoudException, ForbiddenException;

    UsuarioDTO getUsuarioById(Long id) throws ForbiddenException;
}
