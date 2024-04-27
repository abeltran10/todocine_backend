package com.todocine;

import com.todocine.dao.MovieDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.service.MovieService;
import com.todocine.utils.Paginator;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CheckMoviesTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckMoviesTest.class);

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private MovieService movieService;

    @Test
    void findMovieById() {
        LOG.info("findMovieById");

        MovieDTO movieDTO = movieService.getMovieById("13");

        assertEquals("13", movieDTO.getId());
    }

    @Test
    void findMovieByName() {
        LOG.info("findMovieByName");

        Paginator<MovieDTO> paginator = movieService.getMovieByName("star wars", 1);

        assertTrue(paginator.getResults() != null);
    }

    @Test
    void findMoviesPlayingNow() {
        LOG.info("findMoviesPlayingNow");

        Paginator<MovieDTO> paginator = movieService.getMoviesPlayingNow("ES", 1);

        assertTrue(paginator.getResults() != null);
    }
}
