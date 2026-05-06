package com.todocine.service.impl;

import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.response.UsuarioDTO;
import com.todocine.dto.request.UsuarioReqDTO;
import com.todocine.entities.Usuario;
import com.todocine.exceptions.ConflictException;
import com.todocine.exceptions.ForbiddenException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.UsuarioService;
import com.todocine.utils.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.todocine.configuration.Constants.*;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UsuarioService {

    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UsuarioDAO usuarioDAO;


    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);

        Usuario usuario = usuarioDAO.findByUsername(username);

        if (usuario == null)
            throw new UsernameNotFoundException(USER_PASSWORD_ERROR);
        else
            return usuario;

    }

    @Override
    @Transactional
    public UsuarioDTO insertUsuario(UsuarioReqDTO usuarioReqDTO) {
        Usuario usuario = usuarioDAO.findByUsername(usuarioReqDTO.getUsername());

        if (usuario == null) {
            usuario = new Usuario();
            usuario.setUsername(usuarioReqDTO.getUsername());
            usuario.setPassword(passwordEncoder().encode(usuarioReqDTO.getPassword()));
            usuario.setEnabled(true);
            usuario.setCredentialsNonExpired(true);
            usuario.setAccountNonLocked(true);
            usuario.setAccountNonExpired(true);
            usuario.setRol("USUARIO");

            usuarioDAO.save(usuario);

            return UserMapper.toDTO(usuario);

        } else {
            throw new ConflictException(USER_EXISTS);
        }
    }

    @Override
    @Transactional
    public UsuarioDTO updateUsuario(Long id, UsuarioReqDTO usuarioReqDTO) {
       log.info("updateUsuario");
        Usuario usuario = null;

        if (getCurrentUserId().equals(id)) {
            try {
                usuario = usuarioDAO.findById(id).get();

                usuario.setPassword(passwordEncoder().encode(usuarioReqDTO.getPassword()));

                usuarioDAO.save(usuario);

                return UserMapper.toDTO(usuario);
            } catch (NoSuchElementException ex) {
                throw new NotFoudException(USER_NOTFOUND);
            }

        } else {
            throw new ForbiddenException(USER_FORBIDDEN);
        }

    }


    @Override
    public UsuarioDTO getUsuarioById(Long id) {
        if (getCurrentUserId().equals(id)) {

            try {
                Usuario usuario = usuarioDAO.findById(id).get();

                return UserMapper.toDTO(usuario);
            } catch (NoSuchElementException ex) {
                throw new NotFoudException(USER_NOTFOUND);
            }

        } else {
            throw new ForbiddenException(USER_FORBIDDEN);
        }
    }
}
