package com.todocine;

import com.todocine.dao.CategoriaDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dao.PremioDAO;
import com.todocine.entities.Categoria;
import com.todocine.entities.Movie;
import com.todocine.entities.Premio;
import com.todocine.service.MovieService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
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
    private MovieService movieService;

    private Premio p = null;
    @BeforeAll
    void setUp() {
        premioDAO.deleteAll();
        movieDAO.deleteAll();

        Movie movie = new Movie(movieService.getMovieById("906126"));
        movieDAO.save(movie);

        p = new Premio(null, 1, null, "Goya");
        premioDAO.save(p);

        Categoria categoria = new Categoria(null, new Premio(p.getId()), "Mejor película",  movie);
        categoriaDAO.save(categoria);


    }

    @Test
    void getPremioById() {
        Premio premio = premioDAO.findById(p.getId()).orElse(null);

        LOG.info(premio.getCategorias().get(0).getMovie().getOriginalTitle());

        assertEquals(1, premio.getCategorias().size());
    }
}
