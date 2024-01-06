package com.todocine.controller;

import com.todocine.model.Movie;
import com.todocine.model.MoviePage;
import com.todocine.service.MovieService;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/movie")
public class MovieController {
    Logger LOG = LoggerFactory.getLogger(MovieController.class);
    @Autowired
    private MovieService movieService;

    @GetMapping("/name/{name}")
    public MoviePage getMovieByName(@NotBlank @PathVariable("name") String name, @RequestParam("page") Integer pagina) throws ResponseStatusException {
        return movieService.getMovieByName(name, pagina);
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@NotBlank @PathVariable("id") String id) throws ResponseStatusException {
        Movie movie = null;

        movie = movieService.getMovieById(id);

        return movie;
    }

    @GetMapping("now/{region}")
    public MoviePage getMoviesPlayingNow(@NotBlank @PathVariable("region") String region, @RequestParam("page") Integer pagina) throws ResponseStatusException {
        return movieService.getMoviesPlayingNow(region, pagina);
    }

}
