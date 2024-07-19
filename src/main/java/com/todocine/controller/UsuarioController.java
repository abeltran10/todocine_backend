package com.todocine.controller;

import com.todocine.dto.FavoritosDTO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.dto.MovieDTO;
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

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/username/{username}")
    public ResponseEntity<UsuarioDTO> getUsuarioByName(@NotBlank @PathVariable("username") String username) throws NotFoudException {
        logger.info("getUsuarioByName controller");
        ResponseEntity<UsuarioDTO> responseEntity = new ResponseEntity<>(usuarioService.getUsuarioByName(username), HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> insertUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) throws BadRequestException {
        ResponseEntity<UsuarioDTO> responseEntity = new ResponseEntity<>(usuarioService.insertUsuario(usuarioDTO), HttpStatus.CREATED);
        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@NotNull @PathVariable("id") Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) throws NotFoudException {
        logger.info("updateUsuario");
        ResponseEntity<UsuarioDTO> responseEntity = new ResponseEntity<>(usuarioService.updateUsuario(id, usuarioDTO), HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/{id}/favs")
    public ResponseEntity<Paginator<MovieDTO>> getUsuarioFavs(@NotNull @PathVariable("id") Long id, @RequestParam("page") Integer pagina) throws NotFoudException {
        ResponseEntity<Paginator<MovieDTO>> responseEntity = new ResponseEntity<>(usuarioService.getUsuarioFavs(id, pagina), HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping("/{id}/favs")
    public ResponseEntity<FavoritosDTO> addFavoritosByUserId(@NotNull @PathVariable("id") Long id, @Valid @RequestBody MovieDTO movieDTO) throws BadRequestException, NotFoudException {
        logger.info("addFavoritosByUserId");
        ResponseEntity<FavoritosDTO> responseEntity = new ResponseEntity<>(usuarioService.addFavoritosByUserId(id, movieDTO), HttpStatus.CREATED);
        return responseEntity;
    }

    @DeleteMapping("/{id}/favs/{movieId}")
    public ResponseEntity deleteFavoritosByUserId(@NotNull @PathVariable("id") Long id, @NotBlank @PathVariable("movieId") String movieId) throws BadRequestException, NotFoudException {
        logger.info("deleteFavoritosByUserId");
        usuarioService.deleteFavoritosByUserId(id, movieId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
