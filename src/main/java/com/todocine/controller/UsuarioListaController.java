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
@RequestMapping("/usuarios/{usuarioId}/listas")
public class UsuarioListaController {

    @Autowired
    private ListaService listaService;

    @GetMapping
    public ResponseEntity<Paginator<ListaDTO>> getListasUser(@NotNull @PathVariable("usuarioId") Long usuarioId,
                                                             @NotNull @RequestParam("page") Integer page) {
        return new ResponseEntity<>(listaService.getListasUser(usuarioId, page), HttpStatus.OK);
    }
}
