package com.todocine;


import com.todocine.dao.PremioDAO;
import com.todocine.dto.CategoriaDTO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.PremioDTO;
import com.todocine.model.Premio;
import com.todocine.service.impl.PremioServiceImpl;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CheckPremioUnitarioTest {

    public static Logger LOG = LoggerFactory.getLogger(CheckPremioUnitarioTest.class);

    @Mock
    PremioDAO premioDAO;

    @InjectMocks
    PremioServiceImpl premioService;

    static PremioDTO premioDTO;

    @BeforeAll
    static void setUp() {
        MovieDTO movieDTO1 = new MovieDTO("123");
        movieDTO1.setTitle("La sociedad de la nieve");
        movieDTO1.setGenreIds(new ArrayList<>());
        movieDTO1.setVideos(new ArrayList<>());
        movieDTO1.setVotosTC(new ArrayList<>());

        CategoriaDTO categoriaDTO1 = new CategoriaDTO();
        categoriaDTO1.setNombre("Mejor Película");
        categoriaDTO1.setMovieDTO(movieDTO1);

        CategoriaDTO categoriaDTO2 = new CategoriaDTO();
        categoriaDTO2.setNombre("Mejor Director");
        categoriaDTO2.setMovieDTO(movieDTO1);

        List<CategoriaDTO> categoriaDTOS = Arrays.asList(categoriaDTO1, categoriaDTO2);

        PremioDTO premioDTO1 = new PremioDTO("1");
        premioDTO1.setTitulo("Goya");
        premioDTO1.setCategoriaDTOS(categoriaDTOS);

        premioDTO = premioDTO1;
    }

    @Test
    void getPremioById() {
        LOG.info("getPremioById");

        Premio premio = new Premio(premioDTO);

        Mockito.when(premioDAO.findById("1")).thenReturn(Optional.of(premioDTO));

        Premio premio1 = premioService.getPremioById("1");

        assertEquals(premio.getId(), premio1.getId());
    }

}
