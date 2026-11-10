package com.todocine.controller;

import com.todocine.dto.response.ListaDTO;
import com.todocine.dto.response.Paginator;
import com.todocine.service.ListaService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios/{username}/listas")
@Validated // <-- CRUCIAL para que ConstraintViolationException funcione en parámetros
public class UsuarioListaController {

    @Autowired
    private ListaService listaService;

    @GetMapping
    public ResponseEntity<Paginator<ListaDTO>> getListasUser(@NotBlank @PathVariable("username") String username,
                                                             @NotNull @Min(1) @RequestParam("page") Integer page) {
        return new ResponseEntity<>(listaService.getListasUser(username, page), HttpStatus.OK);
    }
}
