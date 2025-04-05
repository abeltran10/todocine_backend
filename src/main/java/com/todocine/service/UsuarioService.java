package com.todocine.service;

import com.todocine.dto.MovieDTO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.utils.Paginator;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService extends UserDetailsService {

    UsuarioDTO getUsuarioById(Long id) throws NotFoudException;

    UsuarioDTO getUsuarioByName (String username) throws NotFoudException, BadRequestException;

    UsuarioDTO insertUsuario(UsuarioDTO usuarioDTO) throws BadRequestException;

    UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO) throws NotFoudException, BadRequestException;

    UsuarioDTO getUsuario(Long id) throws BadRequestException;
}
