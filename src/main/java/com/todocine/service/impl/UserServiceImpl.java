package com.todocine.service.impl;

import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.entities.Usuario;
import com.todocine.exceptions.BadGatewayException;
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

import java.util.NoSuchElementException;

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
            throw new UsernameNotFoundException("Usuario o contraseña incorrectos");
        else
            return usuario;

    }


    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO getUsuarioByName(String username) throws NotFoudException, BadRequestException {
        log.info("getUsuarioByName");

        Usuario usuario = usuarioDAO.findByUsername(username);

        if (usuario == null)
            throw new NotFoudException("No existe el usuario con ese nombre");
        else if (!getCurrentUserId().equals(usuario.getId()))
            throw new BadRequestException("El usuario solicitado no es el de la sesión");
        else {
            UsuarioDTO usuarioDTO = UserMapper.toDTO(usuario);

            return usuarioDTO;
        }
    }

    @Override
    @Transactional
    public UsuarioDTO insertUsuario(UsuarioDTO usuarioDTO) throws BadRequestException {
        Usuario usuario = usuarioDAO.findByUsername(usuarioDTO.getUsername());

        if (usuario == null) {
            usuario = new Usuario();
            usuario.setUsername(usuarioDTO.getUsername());
            usuario.setPassword(passwordEncoder().encode(usuarioDTO.getPassword()));
            usuario.setEnabled(true);
            usuario.setCredentialsNonExpired(true);
            usuario.setAccountNonLocked(true);
            usuario.setAccountNonExpired(true);

            usuarioDAO.save(usuario);

            return UserMapper.toDTO(usuario);
        } else {
            throw new BadRequestException("Un usuario con ese nombre ya existe");
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
                throw new NotFoudException("No existe el usuario");
            }
        } else {
            throw new ForbiddenException("El usuario no es el de la sesión");
        }

    }


    @Override
    public UsuarioDTO getUsuarioById(Long id) throws ForbiddenException {
        if (getCurrentUserId().equals(id)) {
            Usuario usuario = usuarioDAO.findById(id).get();

            UsuarioDTO usuarioDTO = UserMapper.toDTO(usuario);

            return usuarioDTO;
        } else {
            throw new ForbiddenException("El usuario no es el de la sesión");
        }
    }
}
