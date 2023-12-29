package com.todocine.controller;

import com.todocine.dto.UsuarioDTO;
import com.todocine.model.Usuario;
import com.todocine.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{username}")
    public Usuario getUsuario(@NotBlank @PathVariable("username") String username) {
        logger.info("getUsuario");
        return new Usuario((UsuarioDTO) usuarioService.loadUserByUsername(username));
    }

    @PostMapping
    public Usuario insertUsuario(@Valid @RequestBody Usuario usuario) {
        return usuarioService.insertUsuario(usuario);
    }


}
