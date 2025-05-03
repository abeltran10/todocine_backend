package com.todocine;

import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.MovieDetailDTO;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import com.todocine.service.MovieService;
import com.todocine.service.TMDBService;
import com.todocine.utils.Paginator;
import com.todocine.utils.mapper.MovieMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckMoviesTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckMoviesTest.class);

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private MovieService movieService;

    @Autowired
    private TMDBService tmdbService;

    private Usuario usuario;

    @BeforeAll
    void setUp() {
        usuarioDAO.deleteAll();

        usuario = new Usuario("test", "1234");
        usuarioDAO.save(usuario);
        try {
            MovieDTO movieDTO = MovieMapper.toDTO(tmdbService.getMovieById("906126"));
            Movie movie = MovieMapper.toEntity(movieDTO);
            movieDAO.save(movie);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void findMovieById() {
        LOG.info("findMovieById");

        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword());
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        MovieDetailDTO movieDetailDTO = movieService.getMovieDetailById("906126");

        assertEquals("906126", movieDetailDTO.getId());
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
