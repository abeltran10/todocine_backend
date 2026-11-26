package com.todocine.controller;


import com.todocine.dto.response.ComentarioDTO;
import com.todocine.service.ComentarioService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("comentarios")
@Validated // <-- CRUCIAL para que ConstraintViolationException funcione en parámetros
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/{id}")
    public ResponseEntity<List<ComentarioDTO>> getComentarioReplies(@NotNull @PathVariable("id") Long id) {
        return null;
    }
}
