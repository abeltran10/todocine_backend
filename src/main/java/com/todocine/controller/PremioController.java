package com.todocine.controller;

import com.todocine.dto.PremioDTO;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.PremioService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/premio")
public class PremioController {

    @Autowired
    private PremioService premioService;


    @GetMapping("/{codigo}")
    public ResponseEntity<PremioDTO> getPremioByCodigo(@NotNull @PathVariable("codigo") Integer codigo) throws NotFoudException {
        ResponseEntity<PremioDTO> responseEntity = new ResponseEntity<>(premioService.getPremioByCodigo(codigo), HttpStatus.OK);

        return responseEntity;
    }
}
