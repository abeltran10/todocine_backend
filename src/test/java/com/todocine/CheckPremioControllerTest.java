package com.todocine;

import com.todocine.configuration.Constants;
import com.todocine.controller.PremioController;
import com.todocine.dao.*;
import com.todocine.dto.GanadorDTO;
import com.todocine.dto.MovieDTO;
import com.todocine.entities.*;
import com.todocine.exceptions.NotFoudException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
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
    private TMDBService tmdbService;

    @Autowired
    private UsuarioMovieDAO favoritosDAO;

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
        Movie movie = null;

        try {
            MovieDTO movieDTO = MovieMapper.toDTO(tmdbService.getMovieById("906126"));
            movie = MovieMapper.toEntity(movieDTO);
            movieDAO.save(movie);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


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
        ResponseEntity<Paginator<GanadorDTO>> responseEntity = premioController.getPremioByCodigoAnyo(premioId, 2024, 1);

        Paginator<GanadorDTO> ganadorDTOList = responseEntity.getBody();

        assertEquals("La sociedad de la nieve", ganadorDTOList.getResults().get(0).getTitle());

    }

    @Test
    void getPremioByCodigoException() {
        try {
            ResponseEntity<Paginator<GanadorDTO>> paginator = premioController.getPremioByCodigoAnyo(0L, 2023, 1);
        } catch (NotFoudException ex) {
            assertEquals(Constants.PREMIO_NOTFOUND, ex.getMessage());
        }



    }

}
