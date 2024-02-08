package com.todocine.controller;

import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.model.Movie;
import com.todocine.model.Voto;
import com.todocine.service.MovieService;
import com.todocine.utils.Paginator;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {
    Logger LOG = LoggerFactory.getLogger(MovieController.class);
    @Autowired
    private MovieService movieService;

    @GetMapping("/search")
    public ResponseEntity<Paginator<Movie>> getMovieByName(@NotBlank @RequestParam("name") String name, @RequestParam("page") Integer pagina)
            throws NotFoudException, BadGatewayException {
        Paginator<Movie> paginator = movieService.getMovieByName(name, pagina);
        ResponseEntity<Paginator<Movie>> responseEntity = new ResponseEntity<>(paginator, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@NotBlank @PathVariable("id") String id) throws NotFoudException, BadGatewayException {
        ResponseEntity<Movie> responseEntity = new ResponseEntity<>(movieService.getMovieById(id), HttpStatus.OK);
        return responseEntity;

    }

    @GetMapping("/now")
    public ResponseEntity<Paginator<Movie>> getMoviesPlayingNow(@NotBlank @RequestParam("region") String region, @RequestParam("page") Integer pagina)
            throws NotFoudException, BadGatewayException {
        Paginator<Movie> paginator = movieService.getMoviesPlayingNow(region, pagina);
        ResponseEntity<Paginator<Movie>> responseEntity = new ResponseEntity<>(paginator, HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping("/{id}/vote")
    public ResponseEntity<Movie> votar(@NotBlank @PathVariable("id") String movieId, @RequestBody Voto voto) throws BadGatewayException {
        ResponseEntity<Movie> responseEntity = new ResponseEntity<>(movieService.addVote(movieId, voto), HttpStatus.CREATED);
        return responseEntity;
    }

    @PutMapping("/{id}/vote/{votoId}")
    public ResponseEntity<Movie> actualizarVoto(@NotBlank @PathVariable("id") String movieId, @PathVariable("votoId") String votoId, @RequestBody Voto voto)
            throws NotFoudException {
        ResponseEntity<Movie> responseEntity = new ResponseEntity<>(movieService.updateVote(movieId, votoId, voto), HttpStatus.OK);
        return responseEntity;
    }


}
