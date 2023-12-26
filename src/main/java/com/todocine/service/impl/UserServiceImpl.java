package com.todocine.service.impl;

import com.todocine.dao.UsuarioDAO;
import com.todocine.model.Usuario;
import com.todocine.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UsuarioService {

    @Autowired
    UsuarioDAO usuarioDAO;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioDAO.findUsuarioDTOByUsername(username);
    }

    @Override
    public Usuario getUsuarioById(String id) {
        return new Usuario(usuarioDAO.findUsuarioDTOById(id));
    }
}
