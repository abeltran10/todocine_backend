package com.todocine.controller;

import com.todocine.dto.response.ListaDTO;
import com.todocine.dto.response.Paginator;
import com.todocine.service.ListaService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios/{usuarioId}/listas")
@Validated // <-- CRUCIAL para que ConstraintViolationException funcione en parámetros
public class UsuarioListaController {

    @Autowired
    private ListaService listaService;

    @GetMapping
    public ResponseEntity<Paginator<ListaDTO>> getListasUser(@NotNull @PathVariable("usuarioId") Long usuarioId,
                                                             @NotNull @RequestParam("page") Integer page) {
        return new ResponseEntity<>(listaService.getListasUser(usuarioId, page), HttpStatus.OK);
    }
}
