package com.todocine.controller;

import com.todocine.dto.GanadorDTO;
import com.todocine.dto.PremioAnyoDTO;
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


    @GetMapping("/{id}/anyo/{anyo}")
    public ResponseEntity<List<GanadorDTO>> getPremioByCodigoAnyo(@NotNull @PathVariable("id") Long id, @NotNull @PathVariable("anyo") Integer anyo) throws NotFoudException {
        ResponseEntity<List<GanadorDTO>> responseEntity = new ResponseEntity<>(premioService.getPremioByCodigoAnyo(id, anyo), HttpStatus.OK);

        return responseEntity;
    }

    @GetMapping
    public ResponseEntity<List<PremioAnyoDTO>> getPremios() throws NotFoudException {
        ResponseEntity<List<PremioAnyoDTO>> responseEntity = new ResponseEntity<>(premioService.getPremios(), HttpStatus.OK);

        return responseEntity;
    }
}
