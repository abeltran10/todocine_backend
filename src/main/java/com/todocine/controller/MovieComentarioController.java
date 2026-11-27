package com.todocine.controller;

import com.todocine.dto.request.ComentarioReqDTO;
import com.todocine.dto.response.ComentarioDTO;
import com.todocine.service.ComentarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movies/{id}/comentarios")
@Validated // <-- CRUCIAL para que ConstraintViolationException funcione en parámetros
public class MovieComentarioController {

    @Autowired
    private ComentarioService comentarioService;


    @GetMapping
    public ResponseEntity<List<ComentarioDTO>> getComentariosByMovie(@NotNull @PathVariable("id") Long id) {
        return new ResponseEntity<>(comentarioService.getComentariosByMovie(id), HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<ComentarioDTO> guardarComentario(@NotNull @PathVariable("id") Long id,
                                                           @Valid @RequestBody ComentarioReqDTO comentarioReqDTO) {
        return new ResponseEntity<>(comentarioService.guardarComentario(id, comentarioReqDTO), HttpStatus.OK);

    }
}
