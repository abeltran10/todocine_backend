package com.todocine.controller;


import com.todocine.configuration.Constants;
import com.todocine.dto.request.UsuarioMovieDTO;
import com.todocine.dto.response.MovieDetailDTO;
import com.todocine.service.UsuarioMovieService;
import com.todocine.utils.Paginator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuarios/{userId}/movies")
public class UsuarioMovieController {

    @Autowired
    private UsuarioMovieService usuarioMovieService;

    @PutMapping("/{movieId}")
    public ResponseEntity<MovieDetailDTO> updateUsuarioMovie(@NotNull @PathVariable("userId") Long userId,
                                                             @NotNull @PathVariable("movieId") Long movieId,
                                                             @Valid @RequestBody UsuarioMovieDTO usuarioMovieDTO) {
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

    @GetMapping
    public ResponseEntity<Paginator<MovieDetailDTO>> getUsuarioMovies(@NotNull @PathVariable("userId") Long userId,
                                                                      @RequestParam("vista") String vista,
                                                                      @RequestParam("votada") String votada,
                                                                      @RequestParam("orderBy") String orderBy,
                                                                      @RequestParam("page") Integer pagina) {
        Map<String, String> filters = new HashMap<>();
        filters.put(Constants.VISTA_FILTER, vista);
        filters.put(Constants.VOTADA_FILTER, votada);

        ResponseEntity<Paginator<MovieDetailDTO>> responseEntity = new ResponseEntity<>(usuarioMovieService.getUsuarioMovies(userId, filters, orderBy, pagina), HttpStatus.OK);
        return responseEntity;
    }

}
