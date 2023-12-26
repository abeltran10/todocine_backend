package com.todocine.controller;

import com.todocine.dto.UsuarioDTO;
import com.todocine.model.Usuario;
import com.todocine.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{username}")
    public Usuario getUsuario(@Valid @PathVariable("username") String username) {
        return new Usuario((UsuarioDTO) usuarioService.loadUserByUsername(username));
    }


}
