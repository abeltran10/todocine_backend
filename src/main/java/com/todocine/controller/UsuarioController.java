package com.todocine.controller;

import com.todocine.configuration.Constants;
import com.todocine.dto.MovieDetailDTO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.exceptions.ConflictException;
import com.todocine.exceptions.ForbiddenException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.UsuarioMovieService;
import com.todocine.service.UsuarioService;
import com.todocine.utils.Paginator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioMovieService usuarioMovieService;


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuario(@NotNull @PathVariable("id") Long id) throws ForbiddenException, NotFoudException {
        ResponseEntity<UsuarioDTO> responseEntity = new ResponseEntity<>(usuarioService.getUsuarioById(id), HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> insertUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) throws ConflictException {
        ResponseEntity<UsuarioDTO> responseEntity = new ResponseEntity<>(usuarioService.insertUsuario(usuarioDTO), HttpStatus.CREATED);
        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@NotNull @PathVariable("id") Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) throws NotFoudException, ForbiddenException {
        logger.info("updateUsuario");
        ResponseEntity<UsuarioDTO> responseEntity = new ResponseEntity<>(usuarioService.updateUsuario(id, usuarioDTO), HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/{userId}/movies")
    public ResponseEntity<Paginator<MovieDetailDTO>> getUsuarioMovies(@NotNull @PathVariable("userId") Long userId,
                                                                      @RequestParam("vista") String vista,
                                                                      @RequestParam("votada") String votada,
                                                                      @RequestParam("orderBy") String orderBy,
                                                                      @RequestParam("page") Integer pagina)
            throws ForbiddenException {
        Map<String, String> filters = new HashMap<>();
        filters.put(Constants.VISTA_FILTER, vista);
        filters.put(Constants.VOTADA_FILTER, votada);

        ResponseEntity<Paginator<MovieDetailDTO>> responseEntity = new ResponseEntity<>(usuarioMovieService.getUsuarioMovies(userId, filters, orderBy, pagina), HttpStatus.OK);
        return responseEntity;
    }

}
