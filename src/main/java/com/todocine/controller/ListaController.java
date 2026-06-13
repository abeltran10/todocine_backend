package com.todocine.controller;

import com.todocine.dto.request.ListaReqDTO;
import com.todocine.dto.request.ValoracionListaReqDTO;
import com.todocine.dto.response.ListaDTO;
import com.todocine.dto.response.MovieListaDTO;
import com.todocine.dto.response.ValoracionListaDTO;
import com.todocine.service.ListaService;
import com.todocine.service.ValoracionListaService;
import com.todocine.utils.Paginator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listas")
public class ListaController {

    @Autowired
    private ListaService listaService;

    @Autowired
    private ValoracionListaService valoracionListaService;

    @GetMapping
    public ResponseEntity<Paginator<ListaDTO>> getListasPublicas(@NotNull @RequestParam("page") Integer page) {
        return new ResponseEntity<>(listaService.getListasPublicas(page), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaDTO> getListaById(@NotNull @PathVariable("id") Long id) {
        return new ResponseEntity<>(listaService.getListaById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ListaDTO> createLista(@Valid @RequestBody ListaReqDTO listaDTO) {
        return new ResponseEntity<>(listaService.createLista(listaDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaDTO> updateLista(@NotNull @PathVariable("id") Long id,
                                                @Valid @RequestBody ListaReqDTO listaDTO) {
        return new ResponseEntity<>(listaService.updateLista(id, listaDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLista(@NotNull @PathVariable("id") Long id) {
        listaService.deleteLista(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{listaId}/movies")
    public ResponseEntity<Paginator<MovieListaDTO>> getMoviesByList(@NotNull @PathVariable("listaId") Long listaId,
                                                                    @NotNull @RequestParam("page") Integer pagina) {
        return new ResponseEntity<>(listaService.getMoviesByLista(listaId, pagina), HttpStatus.OK);
    }

    @PostMapping("/{listaId}/movies/{movieId}")
    public ResponseEntity<MovieListaDTO> addMovieToList(
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

    @GetMapping("/{id}/valoraciones")
    public ResponseEntity<List<ValoracionListaDTO>> getListaValoraciones(@NotNull @PathVariable("id") Long id) {
        return new ResponseEntity<>(valoracionListaService.getListaValoraciones(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/valoraciones")
    public ResponseEntity<ValoracionListaDTO> guardarValoracionLista(@NotNull @PathVariable("id") Long listaId,
                                                                    @Valid @RequestBody ValoracionListaReqDTO valoracionListaReqDTO) {

        return new ResponseEntity<>(valoracionListaService.guardarValoracionLista(listaId, valoracionListaReqDTO), HttpStatus.OK);
    }
}
