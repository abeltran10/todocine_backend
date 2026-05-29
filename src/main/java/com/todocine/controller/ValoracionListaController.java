package com.todocine.controller;


import com.todocine.dto.request.ValoracionListaDTO;
import com.todocine.dto.response.ListaDTO;
import com.todocine.dto.response.ValoracionDTO;
import com.todocine.service.ValoracionListaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listas/{listaId}/usuarios")
public class ValoracionListaController {

    @Autowired
    private ValoracionListaService valoracionListaService;

    @PutMapping("/{usuarioId}")
    public ResponseEntity<ValoracionDTO> updateValoracionLista(@NotNull @PathVariable("listaId") Long listaId,
                                                               @NotNull @PathVariable("usuarioId") Long usuarioId,
                                                               @Valid @RequestBody ValoracionListaDTO valoracionListaDTO) {

            return new ResponseEntity<>(valoracionListaService.updateValoracionLista(listaId, usuarioId, valoracionListaDTO), HttpStatus.OK);

    }
}