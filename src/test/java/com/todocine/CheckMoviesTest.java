package com.todocine;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;


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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckMoviesTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckMoviesTest.class);

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private TMDBService tmdbService;

    private Usuario usuario;

    @Autowired
    private MockMvc mockMvc;

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

        try {
            mockMvc.perform(get("/movie/906126")
                     .with(authentication(new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(), usuario.getAuthorities()))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value("906126"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findMovieByName() {
        LOG.info("findMovieByName");

        try {
            mockMvc.perform(get("/movies?name=star wars&page=1")
                            .with(authentication(new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(), usuario.getAuthorities()))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.results").isNotEmpty());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void findMoviesPlayingNow() {
        LOG.info("findMoviesPlayingNow");

        try {
            mockMvc.perform(get("/movies?status=now&region=ES&page=1")
                            .with(authentication(new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(), usuario.getAuthorities()))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.results").isNotEmpty());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
