package com.todocine.controller;


import com.todocine.dto.CategoriaDTO;
import com.todocine.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    @PreAuthorize("ADMIN")
    public ResponseEntity<List<CategoriaDTO>> getCategorias() {
        return new ResponseEntity<>(categoriaService.getCategorias(), HttpStatus.OK);
    }

}
