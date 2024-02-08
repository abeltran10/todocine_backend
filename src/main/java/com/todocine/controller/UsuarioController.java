package com.todocine.controller;

import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.model.Movie;
import com.todocine.model.Usuario;
import com.todocine.service.UsuarioService;
import com.todocine.utils.Paginator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> getUsuarioByName(@NotBlank @PathVariable("username") String username) throws NotFoudException {
        logger.info("getUsuarioByName controller");
        ResponseEntity<Usuario> responseEntity = new ResponseEntity<>(usuarioService.getUsuarioByName(username), HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping
    public ResponseEntity<Usuario> insertUsuario(@Valid @RequestBody Usuario usuario) throws BadRequestException {
        ResponseEntity<Usuario> responseEntity = new ResponseEntity<>(usuarioService.insertUsuario(usuario), HttpStatus.CREATED);
        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@NotBlank @PathVariable("id") String id, @Valid @RequestBody Usuario usuario) throws NotFoudException {
        logger.info("updateUsuario");
        ResponseEntity<Usuario> responseEntity = new ResponseEntity<>(usuarioService.updateUsuario(id, usuario), HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/{id}/favs")
    public ResponseEntity<Paginator<Movie>> getUsuarioFavs(@NotBlank @PathVariable("id") String id, @RequestParam("page") Integer pagina) throws NotFoudException {
        ResponseEntity<Paginator<Movie>> responseEntity = new ResponseEntity<>(usuarioService.getUsuarioFavs(id, pagina), HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping("/{id}/favs")
    public ResponseEntity<Movie> addFavoritosByUserId(@NotBlank @PathVariable("id") String id, @Valid @RequestBody Movie movie) throws BadRequestException, NotFoudException {
        logger.info("addFavoritosByUserId");
        ResponseEntity<Movie> responseEntity = new ResponseEntity<>(usuarioService.addFavoritosByUserId(id, movie), HttpStatus.CREATED);
        return responseEntity;
    }

    @DeleteMapping("/{id}/favs/{movieId}")
    public ResponseEntity deleteFavoritosByUserId(@NotBlank @PathVariable("id") String id, @NotBlank @PathVariable("movieId") String movieId) throws BadRequestException, NotFoudException {
        logger.info("deleteFavoritosByUserId");
        usuarioService.deleteFavoritosByUserId(id, movieId);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
