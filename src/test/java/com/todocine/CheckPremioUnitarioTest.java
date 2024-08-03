package com.todocine;


import com.todocine.dao.PremioDAO;
import com.todocine.dto.PremioDTO;
import com.todocine.entities.Categoria;
import com.todocine.entities.Movie;
import com.todocine.entities.Premio;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CheckPremioUnitarioTest {

    public static Logger LOG = LoggerFactory.getLogger(CheckPremioUnitarioTest.class);

    @Mock
    PremioDAO premioDAO;

    @InjectMocks
    PremioServiceImpl premioService;

    static Premio premio;

    @BeforeAll
    static void setUp() {
        Movie movie1 = new Movie("123");
        movie1.setTitle("La sociedad de la nieve");
        movie1.setVotosTC(new ArrayList<>());

        Categoria categoria1 = new Categoria();
        categoria1.setNombre("Mejor Película");
        categoria1.setMovie(movie1);

        Categoria categoria2 = new Categoria();
        categoria2.setNombre("Mejor Director");
        categoria2.setMovie(movie1);

        List<Categoria> categoriaEntities = Arrays.asList(categoria1, categoria2);

        Premio premio1 = new Premio(1L);
        premio1.setTitulo("Goya");
        premio1.setCategorias(categoriaEntities);

        premio = premio1;
    }

    @Test
    void getPremioByCodigo() {
        LOG.info("getPremioByCodigo");

        PremioDTO premioDTO = new PremioDTO(premio);

        Mockito.when(premioDAO.findByCodigo(1)).thenReturn(premio);

        PremioDTO premioDTO1 = premioService.getPremioByCodigo(1);

        assertEquals(premioDTO.getId(), premioDTO1.getId());
    }

}
