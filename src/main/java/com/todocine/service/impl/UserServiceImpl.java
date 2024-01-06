package com.todocine.service.impl;

import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.model.Usuario;
import com.todocine.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserServiceImpl implements UsuarioService {

    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UsuarioDAO usuarioDAO;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);

        UsuarioDTO usuarioDTO = usuarioDAO.findByUsername(username);

        if (usuarioDTO == null)
            throw new UsernameNotFoundException("Usuario o contraseña incorrectos");
        else
            return usuarioDTO;

    }



    @Override
    public Usuario getUsuarioById(String id) throws ResponseStatusException {
        Usuario usuario = new Usuario();
        UsuarioDTO usuarioDTO = usuarioDAO.findById(id).get();

        if (usuarioDTO == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario con ese nombre");
        else {
            usuario.setId(usuarioDTO.getId());
            usuario.setUsername(usuarioDTO.getUsername());
            usuario.setAccountNonExpired(usuarioDTO.getAccountNonExpired());
            usuario.setAccountNonLocked(usuarioDTO.getAccountNonLocked());
            usuario.setEnabled(usuarioDTO.getEnabled());
            usuario.setCredentialsNonExpired(usuarioDTO.getCredentialsNonExpired());

            return usuario;
        }
    }

    @Override
    public Usuario getUsuarioByName(String username) throws ResponseStatusException {
        log.info("getUsuarioByName");

        Usuario usuario = new Usuario();
        UsuarioDTO usuarioDTO = usuarioDAO.findByUsername(username);

        if (usuarioDTO == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario");
        else {
            usuario.setId(usuarioDTO.getId());
            usuario.setUsername(usuarioDTO.getUsername());
            usuario.setAccountNonExpired(usuarioDTO.getAccountNonExpired());
            usuario.setAccountNonLocked(usuarioDTO.getAccountNonLocked());
            usuario.setEnabled(usuarioDTO.getEnabled());
            usuario.setCredentialsNonExpired(usuarioDTO.getCredentialsNonExpired());

            return usuario;
        }



    }

    @Override
    public Usuario insertUsuario(Usuario usuario) {
        UsuarioDTO usuarioDTO = usuarioDAO.save(new UsuarioDTO(usuario));

        return new Usuario(usuarioDTO);
    }
}
