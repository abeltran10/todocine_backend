package com.todocine.controller;


import com.todocine.dto.VotoComentarioDTO;
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
@RequestMapping("comentarios")
@Validated // <-- CRUCIAL para que ConstraintViolationException funcione en parámetros
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;


    @GetMapping("/{id}/replies")
    public ResponseEntity<List<ComentarioDTO>> getComentarioReplies(@NotNull @PathVariable("id") Long id) {
        return new ResponseEntity<>(comentarioService.getComentarioReplies(id), HttpStatus.OK);
    }


    @PutMapping("/{id}/votos")
    private ResponseEntity<ComentarioDTO> votarComentario(@NotNull @PathVariable("id") Long id,
                                                          @Valid @RequestBody VotoComentarioDTO votoComentarioDTO) {
        return new ResponseEntity<>(comentarioService.votarComentario(id, votoComentarioDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{comentarioId}")
    public ResponseEntity<Void> eliminarComentario(@NotNull @PathVariable("comentarioId") Long comentarioId) {
        comentarioService.deleteComenatario(comentarioId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
