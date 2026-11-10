package com.todocine.controller;

import com.todocine.dto.request.UsuarioReqDTO;
import com.todocine.dto.response.UsuarioDTO;
import com.todocine.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@Validated // <-- CRUCIAL para que ConstraintViolationException funcione en parámetros
public class UsuarioController {
    private Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{username}")
    public ResponseEntity<UsuarioDTO> getUsuario(@NotBlank @PathVariable("username") String username) {
        ResponseEntity<UsuarioDTO> responseEntity = new ResponseEntity<>(usuarioService.getUsuarioById(username), HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> insertUsuario(@Valid @RequestBody UsuarioReqDTO usuarioReqDTO) {
        ResponseEntity<UsuarioDTO> responseEntity = new ResponseEntity<>(usuarioService.insertUsuario(usuarioReqDTO), HttpStatus.CREATED);
        return responseEntity;
    }

    @PutMapping("/{username}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@NotBlank @PathVariable("username") String username, @Valid @RequestBody UsuarioReqDTO usuarioReqDTO) {
        logger.info("updateUsuario");
        ResponseEntity<UsuarioDTO> responseEntity = new ResponseEntity<>(usuarioService.updateUsuario(username, usuarioReqDTO), HttpStatus.OK);
        return responseEntity;
    }



}
