package com.todocine.controller;


import com.todocine.dto.UsuarioMovieDTO;
import com.todocine.dto.MovieDetailDTO;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.UsuarioMovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario/{userId}/movie")
public class UsuarioMovieController {

    @Autowired
    private UsuarioMovieService usuarioMovieService;

    @PutMapping("/{movieId}")
    public ResponseEntity<MovieDetailDTO> updateUsuarioMovie(@NotNull @PathVariable("userId") Long userId,
                                                             @NotBlank @PathVariable("movieId") String movieId,
                                                             @Valid @RequestBody UsuarioMovieDTO usuarioMovieDTO) throws NotFoudException {
        ResponseEntity<MovieDetailDTO> responseEntity = new ResponseEntity<>(usuarioMovieService.updateUsuarioMovie(userId, movieId, usuarioMovieDTO), HttpStatus.OK);
        return responseEntity;
    }

}
