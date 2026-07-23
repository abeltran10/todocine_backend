package com.todocine.controller;

import com.todocine.dto.request.GanadorReqDTO;
import com.todocine.dto.response.GanadorDTO;
import com.todocine.dto.response.Paginator;
import com.todocine.service.GanadorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ganadores")
@Validated // <-- CRUCIAL para que ConstraintViolationException funcione en parámetros
public class GanadorController {

    @Autowired
    private GanadorService ganadorService;


    @GetMapping
    public ResponseEntity<Paginator<GanadorDTO>> getGanadoresByPremioIdAnyo(@NotNull @RequestParam("premioId") Long id,
                                                                            @NotNull @RequestParam("anyo") Integer anyo,
                                                                            @NotNull @RequestParam("pagina") Integer page) {
        ResponseEntity<Paginator<GanadorDTO>> responseEntity = new ResponseEntity<>(ganadorService.getGanadoresByPremioIdAnyo(id, anyo, page), HttpStatus.OK);

        return responseEntity;
    }

    @PostMapping
    @PreAuthorize("ADMIN")
    public ResponseEntity<GanadorDTO> insertGanador(@Valid @RequestBody GanadorReqDTO ganadorReqDTO) {
        ResponseEntity<GanadorDTO> responseEntity = new ResponseEntity<>(ganadorService.insertGanador(ganadorReqDTO), HttpStatus.CREATED);

        return responseEntity;
    }

}
