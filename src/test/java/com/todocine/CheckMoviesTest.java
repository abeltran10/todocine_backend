package com.todocine;

import com.todocine.dao.MovieDAO;
import com.todocine.dto.MovieDTO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CheckMoviesTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckMoviesTest.class);

    @Autowired
    private MovieDAO movieDAO;

    @Test
    void findMovieByUser() {

        List<MovieDTO> foundMovies = movieDAO.findByUserId("658e27b8373bd467ae8bad1a");
        assertTrue(foundMovies != null);
        assertTrue(!foundMovies.isEmpty());

        LOG.info(foundMovies.toString());
    }
}
