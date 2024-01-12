package com.todocine.controller;

import com.todocine.model.Movie;
import com.todocine.utils.Paginator;
import com.todocine.service.MovieService;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/movie")
public class MovieController {
    Logger LOG = LoggerFactory.getLogger(MovieController.class);
    @Autowired
    private MovieService movieService;

    @GetMapping("/search")
    public Paginator<Movie> getMovieByName(@NotBlank @RequestParam("name") String name, @RequestParam("page") Integer pagina) throws ResponseStatusException {
        return movieService.getMovieByName(name, pagina);
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@NotBlank @PathVariable("id") String id) throws ResponseStatusException {
        return movieService.getMovieById(id);

    }

    @GetMapping("/now")
    public Paginator<Movie> getMoviesPlayingNow(@NotBlank @RequestParam("region") String region, @RequestParam("page") Integer pagina) throws ResponseStatusException {
        return movieService.getMoviesPlayingNow(region, pagina);
    }

}
