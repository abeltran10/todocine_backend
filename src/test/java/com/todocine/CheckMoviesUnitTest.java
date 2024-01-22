package com.todocine;

import com.todocine.dao.MovieDAO;
import com.todocine.model.Movie;
import com.todocine.model.Usuario;
import com.todocine.service.TMDBService;
import com.todocine.service.impl.MovieServiceImpl;
import com.todocine.utils.Paginator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CheckMoviesUnitTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckMoviesUnitTest.class);
    @Mock
    private MovieDAO movieDAO;

    @Mock
    private TMDBService tmdbService;

    @InjectMocks
    private MovieServiceImpl movieService;

    private static Movie movie;

    @BeforeAll
    static void setUp() {
        LOG.info("setUp");

        movie = new Movie("13", "Fantasía");

    }

    @Test
    void findMovieById() {
        LOG.info("findMovieById");

        Map<String, Object> movieMap = new HashMap<>();
        movieMap.put("id", "13");
        movieMap.put("original_title", "Fantasía");

        try {
            Mockito.when(tmdbService.getMovieById("13")).thenReturn(movieMap);
            Movie movie = movieService.getMovieById("13");

            assertEquals("13", movie.getId());
            assertEquals("Fantasía", movie.getOriginalTitle());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findMovieByName() {
        LOG.info("findMovieByName");

        Map<String, Object> movieMap = new HashMap<>();
        Map<String, Object> results = new HashMap<>();

        movieMap.put("id", "13");
        movieMap.put("original_title", "Fantasía");

        results.put("results", Arrays.asList(movieMap));
        results.put("page", 1);
        results.put("total_pages", 1);
        results.put("total_results", 1);

        try {
            Mockito.when(tmdbService.getMoviesByName("Fantasía", 1)).thenReturn(results);
            Paginator<Movie> paginator = movieService.getMovieByName("Fantasía", 1);

            assertTrue(paginator.getResults() != null);
            assertEquals("Fantasía", paginator.getResults().get(0).getOriginalTitle());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void findMoviesPlayingNow() {
        LOG.info("findMoviesPlayingNow");

        Map<String, Object> movieMap = new HashMap<>();
        Map<String, Object> results = new HashMap<>();

        movieMap.put("id", "13");
        movieMap.put("original_title", "Fantasía");

        results.put("results", Arrays.asList(movieMap));
        results.put("page", 1);
        results.put("total_pages", 1);
        results.put("total_results", 1);

        try {
            Mockito.when(tmdbService.getMoviesPlayingNow("ES", 1)).thenReturn(results);
            Paginator<Movie> paginator = movieService.getMoviesPlayingNow("ES", 1);

            assertTrue(paginator.getResults() != null);
            assertEquals("Fantasía", paginator.getResults().get(0).getOriginalTitle());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
