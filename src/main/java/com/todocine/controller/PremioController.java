package com.todocine.controller;

import com.todocine.dto.response.CategoriaDTO;
import com.todocine.dto.response.PremioDTO;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.CategoriaPremioService;
import com.todocine.service.PremioService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/premios")
public class PremioController {

    @Autowired
    private PremioService premioService;

    @Autowired
    private CategoriaPremioService categoriaPremioService;

    @GetMapping
    public ResponseEntity<List<PremioDTO>> getPremios() {
        return new ResponseEntity<>(premioService.getPremios(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PremioDTO> getPremioById(@NotNull @PathVariable("id") Long id) {
        return new ResponseEntity<>(premioService.getPremioById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/categorias")
    @PreAuthorize("ADMIN")
    public ResponseEntity<List<CategoriaDTO>> getCategorias(@NotNull @PathVariable("id") Long id) {
        return new ResponseEntity<>(categoriaPremioService.getCategorias(id), HttpStatus.OK);
    }
}
