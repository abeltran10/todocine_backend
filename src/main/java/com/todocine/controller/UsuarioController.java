package com.todocine.controller;

import com.todocine.dto.MovieDTO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.UsuarioService;
import com.todocine.utils.Paginator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuario(@NotNull @PathVariable("id") Long id) throws BadRequestException {
        ResponseEntity<UsuarioDTO> responseEntity = new ResponseEntity<>(usuarioService.getUsuario(id), HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UsuarioDTO> getUsuarioByName(@NotBlank @PathVariable("username") String username) throws NotFoudException, BadRequestException {
        logger.info("getUsuarioByName controller");
        ResponseEntity<UsuarioDTO> responseEntity = new ResponseEntity<>(usuarioService.getUsuarioByName(username), HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> insertUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) throws BadRequestException {
        ResponseEntity<UsuarioDTO> responseEntity = new ResponseEntity<>(usuarioService.insertUsuario(usuarioDTO), HttpStatus.CREATED);
        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@NotNull @PathVariable("id") Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) throws NotFoudException, BadRequestException {
        logger.info("updateUsuario");
        ResponseEntity<UsuarioDTO> responseEntity = new ResponseEntity<>(usuarioService.updateUsuario(id, usuarioDTO), HttpStatus.OK);
        return responseEntity;
    }




}
