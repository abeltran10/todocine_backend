package com.todocine;

import com.todocine.dao.MovieDAO;
import com.todocine.dao.VotoDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.dto.VotoDTO;
import com.todocine.model.Movie;
import com.todocine.model.Usuario;
import com.todocine.model.Voto;
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
    private VotoDAO votoDAO;

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

    @Test
    void addVoto() {
        LOG.info("addVoto");

        Map<String, Object> map =  new HashMap<>();
        map.put("id","13");
        map.put("original_title", "Fantasia");

        Voto voto = new Voto("1", new Usuario("userTest"), new Movie("13"), 2D);

        try {
            Mockito.when(tmdbService.getMovieById("13")).thenReturn(map);
            Mockito.when(movieDAO.findById("13")).thenReturn(Optional.ofNullable(null));

            Movie movie2 = movieService.addVote("13", voto);

            assertEquals(1, movie2.getTotalVotosTC());
            assertEquals(2, movie2.getVotosMediaTC());
            assertEquals("13", movie2.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

//    @Test
//    void updateVoto() {
//        LOG.info("updateVoto");
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("id", "13");
//        map.put("original_title", "Fantasia");
//
//        VotoDTO votoDTO = new VotoDTO("1", new UsuarioDTO("userTest"), new MovieDTO("13"), 2D);
//        MovieDTO movieDTO = new MovieDTO("13");
//        movieDTO.setOriginalTitle("Fantasía");
//        movieDTO.setVotosTC(Arrays.asList(votoDTO));
//        movieDTO.setVotosMediaTC(2D);
//        movieDTO.setTotalVotosTC(4);
//
//        Voto voto = new Voto("1", new Usuario("userTest"), new Movie("13"), 4D);
//
//        try {
//            Mockito.when(tmdbService.getMovieById("13")).thenReturn(map);
//            Mockito.when(movieDAO.findById("13")).thenReturn(Optional.of(movieDTO));
//            Mockito.when(votoDAO.findById("1")).thenReturn(Optional.of(votoDTO));
//
//            Movie movie2 = movieService.updateVote("13", "1", voto);
//
//            assertEquals(4, movie2.getTotalVotosTC());
//            assertEquals(Double.valueOf(10D / 4), movie2.getVotosMediaTC(), 0.05);
//            assertEquals("13", movie2.getId());
//
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
//    }

}
