package com.todocine.controller;


import com.todocine.dto.request.ComentarioReqDTO;
import com.todocine.dto.request.VotoComentarioReqDTO;
import com.todocine.dto.response.ComentarioDTO;
import com.todocine.dto.response.VotoComentarioDTO;
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
@RequestMapping("comentarios")
@Validated // <-- CRUCIAL para que ConstraintViolationException funcione en parámetros
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;


    @GetMapping("/{id}/replies")
    public ResponseEntity<List<ComentarioDTO>> getComentarioReplies(@NotNull @PathVariable("id") Long id) {
        return new ResponseEntity<>(comentarioService.getComentarioReplies(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ComentarioDTO> insertarComentario(@Valid @RequestBody ComentarioReqDTO comentarioReqDTO) {
        return new ResponseEntity<>(comentarioService.insertarComentario(comentarioReqDTO), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ComentarioDTO> updateComentario(@NotNull @PathVariable("id") Long id,
                                                       @Valid @RequestBody ComentarioReqDTO comentarioReqDTO) {
        return new ResponseEntity<>(comentarioService.updateComentario(id, comentarioReqDTO), HttpStatus.OK);

    }

    @PutMapping("/{id}/votos")
    private ResponseEntity<VotoComentarioDTO> votarComentario(@NotNull @PathVariable("id") Long id,
                                                              @Valid @RequestBody VotoComentarioReqDTO votoComentarioReqDTO) {
        return new ResponseEntity<>(comentarioService.votarComentario(id, votoComentarioReqDTO), HttpStatus.OK);
    }
}
