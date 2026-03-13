package com.todocine.controller;


import com.todocine.dto.request.UsuarioMovieDTO;
import com.todocine.dto.response.MovieDetailDTO;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.ForbiddenException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.UsuarioMovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios/{userId}/movies")
public class UsuarioMovieController {

    @Autowired
    private UsuarioMovieService usuarioMovieService;

    @PutMapping("/{movieId}")
    public ResponseEntity<MovieDetailDTO> updateUsuarioMovie(@NotNull @PathVariable("userId") Long userId,
                                                             @NotNull @PathVariable("movieId") Long movieId,
                                                             @Valid @RequestBody UsuarioMovieDTO usuarioMovieDTO)
            throws NotFoudException, ForbiddenException, BadGatewayException {
        ResponseEntity<MovieDetailDTO> responseEntity = new ResponseEntity<>(usuarioMovieService.updateUsuarioMovie(userId,
                movieId, usuarioMovieDTO), HttpStatus.OK);
        return responseEntity;
    }

    /*@DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteUsuarioMovie(@NotNull @PathVariable("userId") Long userId,
                                               @NotNull @PathVariable("movieId") Long movieId) throws NotFoudException,
            ForbiddenException {

            usuarioMovieService.deleteUsuarioMovie(userId, movieId);

            ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return responseEntity;
    }*/

}
