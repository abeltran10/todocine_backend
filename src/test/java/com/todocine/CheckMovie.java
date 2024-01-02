package com.todocine;

import com.todocine.exceptions.BadGateWayException;
import com.todocine.model.MoviePage;
import com.todocine.service.MovieService;
import com.todocine.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CheckMovie {
    public static Logger LOG = LoggerFactory.getLogger(CheckMovie.class);

    @Autowired
    private MovieService movieService;

    @Test
    void getMoviePage() throws BadGateWayException {
        MoviePage moviePage = movieService.getMovieByName("Forrest", 1);

        assertTrue (moviePage.getResults().size() == 20);


    }

}
