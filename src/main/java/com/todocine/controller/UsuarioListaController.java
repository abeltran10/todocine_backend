package com.todocine.controller;

import com.todocine.dto.request.ListaReqDTO;
import com.todocine.dto.response.ListaDTO;
import com.todocine.service.ListaService;
import com.todocine.utils.Paginator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios/{userId}/listas")
public class UsuarioListaController {

    @Autowired
    private ListaService listaService;

    @GetMapping
    public ResponseEntity<Paginator<ListaDTO>> getListas(@NotNull @PathVariable("userId") Long userId, @NotNull @RequestParam("page") Integer page) {
        return new ResponseEntity<>(listaService.getListas(userId, page), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ListaDTO> createLista(@Valid @RequestBody ListaReqDTO listaDTO) {
        return new ResponseEntity<>(listaService.createLista(listaDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaDTO> updateLista(@NotNull @PathVariable("id") Long id, @Valid @RequestBody ListaReqDTO listaDTO) {
        return new ResponseEntity<>(listaService.updateLista(id, listaDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLista(@NotNull @PathVariable("id") Long id) {
        listaService.deleteLista(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{listaId}/movies/{movieId}")
    public ResponseEntity<ListaDTO> addMovieToList(
            @NotNull @PathVariable("listaId") Long listaId,
            @NotNull @PathVariable("movieId") Long movieId) {

        return new ResponseEntity<>(listaService.addMovieToList(listaId, movieId), HttpStatus.OK);
    }

    @DeleteMapping("/{listaId}/movies/{movieId}")
    public ResponseEntity<Void> deleteMovieFromList(
            @NotNull @PathVariable("listaId") Long listaId,
            @NotNull @PathVariable("movieId") Long movieId) {

        listaService.deleteMovieFromList(listaId, movieId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
