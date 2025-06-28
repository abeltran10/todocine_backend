package com.todocine.controller;

import com.todocine.dto.GanadorDTO;
import com.todocine.dto.PremioAnyoDTO;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.PremioService;
import com.todocine.utils.Paginator;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/premios")
public class PremioController {

    @Autowired
    private PremioService premioService;


    @GetMapping("/{id}/anyos/{anyo}")
    public ResponseEntity<Paginator<GanadorDTO>> getPremioByCodigoAnyo(@NotNull @PathVariable("id") Long id,
                                                                       @NotNull @PathVariable("anyo") Integer anyo,
                                                                       @NotNull @RequestParam("pagina") Integer page) throws NotFoudException {
        ResponseEntity<Paginator<GanadorDTO>> responseEntity = new ResponseEntity<>(premioService.getPremioByCodigoAnyo(id, anyo, page), HttpStatus.OK);

        return responseEntity;
    }

    @GetMapping
    public ResponseEntity<List<PremioAnyoDTO>> getPremioAnyos() throws NotFoudException {
        ResponseEntity<List<PremioAnyoDTO>> responseEntity = new ResponseEntity<>(premioService.getPremioAnyos(), HttpStatus.OK);

        return responseEntity;
    }
}
