package com.todocine.controller;


import com.todocine.dto.MovieDTO;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.FavoritosService;
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
@RequestMapping("/favoritos")
public class FavoritosController {
    private Logger logger = LoggerFactory.getLogger(FavoritosController.class);

    @Autowired
    private FavoritosService favoritosService;

    @GetMapping
    public ResponseEntity<Paginator<MovieDTO>> getUsuarioFavs(@RequestParam("page") Integer pagina) throws NotFoudException {
        ResponseEntity<Paginator<MovieDTO>> responseEntity = new ResponseEntity<>(favoritosService.getUsuarioFavs(pagina), HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping
    public ResponseEntity<MovieDTO> addFavoritos(@Valid @RequestBody MovieDTO movieDTO) throws BadRequestException, NotFoudException {
        logger.info("addFavoritosByUserId");
        ResponseEntity<MovieDTO> responseEntity = new ResponseEntity<>(favoritosService.addFavoritos(movieDTO), HttpStatus.CREATED);
        return responseEntity;
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity deleteFavoritosByUserId(@NotBlank @PathVariable("movieId") String movieId) throws BadRequestException, NotFoudException {
        logger.info("deleteFavoritosByUserId");
        favoritosService.deleteFavoritos(movieId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
