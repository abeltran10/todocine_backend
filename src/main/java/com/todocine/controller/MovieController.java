package com.todocine.controller;

import com.todocine.exceptions.BadGateWayException;
import com.todocine.model.Movie;
import com.todocine.model.MoviePage;
import com.todocine.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/name/{name}")
    public MoviePage getMovieByName(@PathVariable("name") String name, @RequestParam("page") Integer pagina) throws BadGateWayException {
        return movieService.getMovieByName(name, pagina);
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable("id") String id) throws BadGateWayException {
        return movieService.getMovieById(id);

    }

}
