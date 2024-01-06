package com.todocine.controller;

import com.todocine.model.Usuario;
import com.todocine.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/username/{username}")
    public Usuario getUsuarioByName(@NotBlank @PathVariable("username") String username) throws ResponseStatusException {
        logger.info("getUsuarioByName controller");

        Usuario usuario =  usuarioService.getUsuarioByName(username);

        return usuario;
    }

    @PostMapping
    public Usuario insertUsuario(@Valid @RequestBody Usuario usuario) {
        return usuarioService.insertUsuario(usuario);
    }


}
