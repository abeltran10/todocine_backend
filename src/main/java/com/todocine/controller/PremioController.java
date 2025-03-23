package com.todocine.controller;

import com.todocine.dto.GanadorDTO;
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

import java.util.List;

@RestController
@RequestMapping("/premio")
public class PremioController {

    @Autowired
    private PremioService premioService;


    @GetMapping("/{codigo}/anyo/{anyo}")
    public ResponseEntity<List<GanadorDTO>> getPremioByCodigoAnyo(@NotNull @PathVariable("codigo") Integer codigo, @NotNull @PathVariable("anyo") Integer anyo) throws NotFoudException {
        ResponseEntity<List<GanadorDTO>> responseEntity = new ResponseEntity<>(premioService.getPremioByCodigoAnyo(codigo, anyo), HttpStatus.OK);

        return responseEntity;
    }
}
