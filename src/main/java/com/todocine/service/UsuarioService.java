package com.todocine.service;

import com.todocine.dto.UsuarioDTO;
import com.todocine.dto.UsuarioReqDTO;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.ConflictException;
import com.todocine.exceptions.ForbiddenException;
import com.todocine.exceptions.NotFoudException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsuarioService extends UserDetailsService {

    UsuarioDTO insertUsuario(UsuarioReqDTO usuarioReqDTO) throws ConflictException, BadRequestException;

    UsuarioDTO updateUsuario(Long id, UsuarioReqDTO usuarioReqDTO) throws NotFoudException, ForbiddenException;

    UsuarioDTO getUsuarioById(Long id) throws ForbiddenException, NotFoudException;
}
