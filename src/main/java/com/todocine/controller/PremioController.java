package com.todocine.controller;

import com.todocine.dto.CategoriaDTO;
import com.todocine.dto.GanadorDTO;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.CategoriaPremioService;
import com.todocine.service.GanadorService;
import com.todocine.utils.Paginator;
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
    private CategoriaPremioService categoriaPremioService;

    @GetMapping("/{id}/categorias")
    @PreAuthorize("ADMIN")
    public ResponseEntity<List<CategoriaDTO>> getCategorias(@NotNull @PathVariable("id") Long id) {
        return new ResponseEntity<>(categoriaPremioService.getCategorias(id), HttpStatus.OK);
    }
}
