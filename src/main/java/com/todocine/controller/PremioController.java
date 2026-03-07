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
    private GanadorService ganadorService;

    @Autowired
    private CategoriaPremioService categoriaPremioService;


    @GetMapping("/{id}/anyos/{anyo}")
    public ResponseEntity<Paginator<GanadorDTO>> getGanadoresByPremioIdAnyo(@NotNull @PathVariable("id") Long id,
                                                                            @NotNull @PathVariable("anyo") Integer anyo,
                                                                            @NotNull @RequestParam("pagina") Integer page) throws NotFoudException {
        ResponseEntity<Paginator<GanadorDTO>> responseEntity = new ResponseEntity<>(ganadorService.getGanadoresByPremioIdAnyo(id, anyo, page), HttpStatus.OK);

        return responseEntity;
    }



    @GetMapping("/{id}/categorias")
    @PreAuthorize("ADMIN")
    public ResponseEntity<List<CategoriaDTO>> getCategorias(@NotNull @PathVariable("id") Long id) {
        return new ResponseEntity<>(categoriaPremioService.getCategorias(id), HttpStatus.OK);
    }
}
