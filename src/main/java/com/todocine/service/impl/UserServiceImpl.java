package com.todocine.service.impl;

import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.entities.Usuario;
import com.todocine.exceptions.BadRequestException;
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

import java.util.ArrayList;
import java.util.List;
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

        List<Usuario> usuario = usuarioDAO.findByUsername(username);

        if (usuario == null)
            throw new UsernameNotFoundException(USER_PASSWORD_ERROR);
        else
            return usuario.get(0);

    }


    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> getUsuarioByName(String username) throws BadRequestException {
        log.info("getUsuarioByName");

        List<UsuarioDTO> usuarioDTOS = new ArrayList<>();

        List<Usuario> usuarios = usuarioDAO.findByUsername(username);

        if (usuarios.isEmpty())
            return usuarioDTOS;
        else if (getCurrentUserId() == null)
            throw new BadRequestException(USER_FORBIDDEN);
        else {
            return usuarios.stream().map(UserMapper::toDTO).toList();
        }
    }

    @Override
    @Transactional
    public UsuarioDTO insertUsuario(UsuarioDTO usuarioDTO) throws BadRequestException {
        Usuario usuario = null;
        List<Usuario> usuarios = usuarioDAO.findByUsername(usuarioDTO.getUsername());

        if (usuarios.isEmpty()) {
            usuario = new Usuario();
            usuario.setUsername(usuarioDTO.getUsername());
            usuario.setPassword(passwordEncoder().encode(usuarioDTO.getPassword()));
            usuario.setEnabled(true);
            usuario.setCredentialsNonExpired(true);
            usuario.setAccountNonLocked(true);
            usuario.setAccountNonExpired(true);
            usuario.setRol("USUARIO");

            usuarioDAO.save(usuario);

            return UserMapper.toDTO(usuario);
        } else {
            throw new BadRequestException(USER_EXISTS);
        }
    }

    @Override
    @Transactional
    public UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO) throws NotFoudException, ForbiddenException {
       log.info("updateUsuario");
        Usuario usuario = null;

        if (getCurrentUserId().equals(id)) {

            try {
                usuario = usuarioDAO.findById(id).get();
                usuario.setPassword(passwordEncoder().encode(usuarioDTO.getPassword()));

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
    public UsuarioDTO getUsuarioById(Long id) throws ForbiddenException, NotFoudException {
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
