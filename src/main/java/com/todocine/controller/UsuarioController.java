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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/username/{username}")
    public Usuario getUsuarioByName(@NotBlank @PathVariable("username") String username) throws NotFoudException {
        logger.info("getUsuarioByName controller");

        Usuario usuario =  usuarioService.getUsuarioByName(username);

        return usuario;
    }

    @PostMapping
    public Usuario insertUsuario(@Valid @RequestBody Usuario usuario) throws BadRequestException {
        return usuarioService.insertUsuario(usuario);
    }

    @PutMapping("/{id}")
    public Usuario updateUsuario(@NotBlank @PathVariable("id") String id, @Valid @RequestBody Usuario usuario) throws NotFoudException {
        logger.info("updateUsuario");

        return usuarioService.updateUsuario(id, usuario);
    }

    @GetMapping("/{id}/favs")
    public Paginator<Movie> getUsuarioFavs(@NotBlank @PathVariable("id") String id, @RequestParam("page") Integer pagina) throws NotFoudException {
        return usuarioService.getUsuarioFavs(id, pagina);
    }

    @PostMapping("/{id}/favs")
    public Usuario addFavoritosByUserId(@NotBlank @PathVariable("id") String id, @Valid @RequestBody Movie movie) throws BadRequestException, NotFoudException {
        logger.info("addFavoritosByUserId");

        return usuarioService.addFavoritosByUserId(id, movie);
    }

    @DeleteMapping("/{id}/favs/{movieId}")
    public void deleteFavoritosByUserId(@NotBlank @PathVariable("id") String id, @NotBlank @PathVariable("movieId") String movieId) throws BadRequestException, NotFoudException {
        logger.info("deleteFavoritosByUserId");

        usuarioService.deleteFavoritosByUserId(id, movieId);
    }


}
