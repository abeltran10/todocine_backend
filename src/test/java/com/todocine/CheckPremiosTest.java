package com.todocine;

import com.todocine.dao.*;
import com.todocine.dto.MovieDTO;
import com.todocine.entities.Categoria;
import com.todocine.entities.Movie;
import com.todocine.entities.Premio;
import com.todocine.service.MovieService;
import com.todocine.service.TMDBService;
import com.todocine.utils.mapper.MovieMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckPremiosTest {

    public static Logger LOG = LoggerFactory.getLogger(CheckPremiosTest.class);

    @Autowired
    private PremioDAO premioDAO;

    @Autowired
    private CategoriaDAO categoriaDAO;

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private UsuarioMovieDAO favoritosDAO;

    @Autowired
    private GanadorDAO ganadorDAO;


    @Autowired
    private MovieService movieService;

    @Autowired
    private TMDBService tmdbService;

    private Premio p = null;

    @BeforeAll
    void setUp() {
        ganadorDAO.deleteAll();
        premioDAO.deleteAll();
        categoriaDAO.deleteAll();
        favoritosDAO.deleteAll();
        movieDAO.deleteAll();

        Movie movie = null;
        try {
            MovieDTO movieDTO = MovieMapper.toDTO(tmdbService.getMovieById("906126"));
            movie = MovieMapper.toEntity(movieDTO);
            movieDAO.save(movie);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        p = new Premio(null, 1, "Goya");
        premioDAO.save(p);

        Categoria categoria = new Categoria(null, "Mejor película");
        categoriaDAO.save(categoria);


    }

    @Test
    void getPremioById() {
        Premio premio = premioDAO.findById(p.getId()).orElse(null);

        LOG.info(premio.getTitulo());

        assertEquals("Goya", premio.getTitulo());
    }
}
