package com.todocine.controller;

import com.todocine.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity getMovie(@PathVariable("name") String name) {
        return movieService.getMovieByName(name);
    }

}
