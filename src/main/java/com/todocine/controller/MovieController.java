package com.todocine.controller;

import com.todocine.exceptions.BadGateWayException;
import com.todocine.model.MoviePage;
import com.todocine.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MoviePage getMovie(@PathVariable("name") String name, @RequestParam("page") Integer pagina) throws BadGateWayException {
        return movieService.getMovieByName(name, pagina);
    }

}
