package com.todocine.controller;

import com.todocine.dto.UsuarioDTO;
import com.todocine.model.Usuario;
import com.todocine.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{username}")
    public Usuario getUsuarioByName(@NotBlank @PathVariable("username") String username) throws ResponseStatusException {
        logger.info("getUsuarioByName");
        Usuario usuario = null;

        try {
            UsuarioDTO usuarioDTO = (UsuarioDTO) usuarioService.loadUserByUsername(username);
            usuario = new Usuario();

            usuario.setId(usuarioDTO.getId());
            usuario.setUsername(usuarioDTO.getUsername());
            usuario.setAccountNonExpired(usuarioDTO.getAccountNonExpired());
            usuario.setAccountNonLocked(usuarioDTO.getAccountNonLocked());
            usuario.setEnabled(usuarioDTO.getEnabled());
            usuario.setCredentialsNonExpired(usuarioDTO.getCredentialsNonExpired());

            return usuario;
        } catch (UsernameNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario o la contraseña son incorrectos");
        }
    }

    @PostMapping
    public Usuario insertUsuario(@Valid @RequestBody Usuario usuario) {
        return usuarioService.insertUsuario(usuario);
    }


}
