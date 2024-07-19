package com.todocine;

import com.todocine.controller.PremioController;
import com.todocine.dao.CategoriaDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dao.PremioDAO;
import com.todocine.dto.PremioDTO;
import com.todocine.entities.Categoria;
import com.todocine.entities.Movie;
import com.todocine.entities.Premio;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.MovieService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckPremioControllerTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckPremioControllerTest.class);

    @Autowired
    private PremioDAO premioDAO;

    @Autowired
    private CategoriaDAO categoriaDAO;

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private MovieService movieService;

    @Autowired
    private PremioController premioController;

    @BeforeAll
    void setUp() {
        premioDAO.deleteAll();
        movieDAO.deleteAll();

        Movie movie = new Movie(movieService.getMovieById("906126"));
        movieDAO.save(movie);

        Premio premio = new Premio(null, 1, null, "Goya");
        premioDAO.save(premio);

        Categoria categoria = new Categoria(null, new Premio(premio.getId()), "Mejor película",  movie);
        categoriaDAO.save(categoria);


    }

    @Test
    void getPremioById() {
        ResponseEntity<PremioDTO> responseEntity = premioController.getPremioByCodigo(1);

        assertEquals("La sociedad de la nieve", responseEntity.getBody().getCategorias().get(0).getMovie().getTitle());

    }

    @Test
    void getPremioByCodigoException() {
        try {
            premioController.getPremioByCodigo(0);
        } catch (NotFoudException ex) {
            assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        }



    }

}
