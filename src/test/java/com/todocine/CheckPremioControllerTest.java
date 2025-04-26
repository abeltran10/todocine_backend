package com.todocine;

import com.todocine.controller.PremioController;
import com.todocine.dao.*;
import com.todocine.dto.GanadorDTO;
import com.todocine.dto.PremioDTO;
import com.todocine.entities.*;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.MovieService;
import com.todocine.utils.mapper.MovieMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

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
    private FavoritosDAO favoritosDAO;

    @Autowired
    private GanadorDAO ganadorDAO;

    @Autowired
    private PremioController premioController;

    private Long premioId;

    @BeforeAll
    void setUp() {
        ganadorDAO.deleteAll();
        categoriaDAO.deleteAll();
        premioDAO.deleteAll();
        favoritosDAO.deleteAll();
        movieDAO.deleteAll();

        Movie movie = MovieMapper.toEntity(movieService.getMovieById("906126"));
        movieDAO.save(movie);

        Premio premio = new Premio(null, 1, "Goya");
        premioDAO.save(premio);
        premioId = premio.getId();

        Categoria categoria = new Categoria(null, "Mejor película");
        categoriaDAO.save(categoria);

        GanadorId ganadorId = new GanadorId(premio, categoria, movie, 2024);
        Ganador ganador = new Ganador();
        ganador.setId(ganadorId);

        ganadorDAO.save(ganador);


    }

    @Test
    void getPremioById() {
        ResponseEntity<List<GanadorDTO>> responseEntity = premioController.getPremioByCodigoAnyo(premioId, 2024);

        List<GanadorDTO> ganadorDTOList = responseEntity.getBody();

        assertEquals("La sociedad de la nieve", ganadorDTOList.get(0).getMovie().getTitle());

    }

    @Test
    void getPremioByCodigoException() {
        try {
            premioController.getPremioByCodigoAnyo(0L, 2023);
        } catch (NotFoudException ex) {
            assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        }



    }

}
