package com.todocine.service.impl;

import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.model.Usuario;
import com.todocine.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UsuarioService {

    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UsuarioDAO usuarioDAO;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);

        List<UsuarioDTO> dtos = usuarioDAO.findByUsername(username);

        log.info(dtos.toString());

        return dtos.get(0);
    }

    @Override
    public Usuario getUsuarioById(String id) {
        return new Usuario(usuarioDAO.findById(id).get());
    }

    @Override
    public Usuario insertUsuario(Usuario usuario) {
        UsuarioDTO usuarioDTO = usuarioDAO.save(new UsuarioDTO(usuario));

        return new Usuario(usuarioDTO);
    }
}
