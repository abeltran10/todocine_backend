package com.todocine;

import com.todocine.configuration.Constants;
import com.todocine.dao.UsuarioMovieDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.MovieDetailDTO;
import com.todocine.entities.Usuario;
import com.todocine.service.TMDBService;
import com.todocine.service.impl.MovieServiceImpl;
import com.todocine.utils.Paginator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckMoviesUnitTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckMoviesUnitTest.class);
    @Mock
    private MovieDAO movieDAO;

    @Mock
    private TMDBService tmdbService;

    @Mock
    private UsuarioMovieDAO favoritosDAO;

    @InjectMocks
    private MovieServiceImpl movieService;

    private static MovieDTO movieDTO;

    @BeforeAll
    static void setUp() {
        LOG.info("setUp");

        movieDTO = new MovieDTO("13", "Fantasía");

    }

    @Test
    void findMovieById() {
        LOG.info("findMovieById");

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(new Usuario(1L));

        // Mock del SecurityContext
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        // Setear el contexto de seguridad
        SecurityContextHolder.setContext(securityContext);

        Map<String, Object> movieMap = new HashMap<>();
        movieMap.put("id", "13");
        movieMap.put("original_title", "Fantasía");

        try {
            Mockito.when(tmdbService.getMovieById("13")).thenReturn(movieMap);
            Mockito.when(movieDAO.findById("13")).thenReturn(Optional.empty());
            MovieDetailDTO movieDetailDTO = movieService.getMovieDetailById("13");

            assertEquals("13", movieDetailDTO.getId());
            assertEquals("Fantasía", movieDetailDTO.getOriginalTitle());
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

        Map<String, String> filters = new HashMap<>();
        filters.put(Constants.MOVIE_NAME, "Fantasía");
        filters.put(Constants.MOVIE_STATUS, "");
        filters.put(Constants.MOVIE_REGION, "");

        try {
            Mockito.when(tmdbService.getMoviesByName("Fantasía", 1)).thenReturn(results);
            Paginator<MovieDTO> paginator = movieService.getMovies(filters, 1);

            assertTrue(paginator.getResults() != null);
            assertEquals("Fantasía", paginator.getResults().get(0).getOriginalTitle());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
