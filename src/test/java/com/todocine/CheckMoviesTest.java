package com.todocine;

import com.todocine.dao.MovieDAO;
import com.todocine.service.TMDBService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CheckMoviesTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckMoviesTest.class);

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private TMDBService tmdbService;

    @Test
    void getTMDBMovieById() {
        LOG.info("getTMDBMovieById");

        try {
            Map<String, Object> movieMap = tmdbService.getMovieById("x");

            assertThat(movieMap.get("id")).isNull();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
