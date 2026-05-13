package com.todocine.controller;

import com.todocine.dto.response.ListaDTO;
import com.todocine.service.ListaService;
import com.todocine.utils.Paginator;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listas")
public class ListaController {

    @Autowired
    private ListaService listaService;

    @GetMapping
    public ResponseEntity<Paginator<ListaDTO>> getListasPublicas(@NotNull @RequestParam("page") Integer page) {
        return new ResponseEntity<>(listaService.getListasPublicas(page), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaDTO> getLista(@NotNull @PathVariable("id") Long id) {
        return new ResponseEntity<>(listaService.getListaById(id), HttpStatus.OK);
    }
}
