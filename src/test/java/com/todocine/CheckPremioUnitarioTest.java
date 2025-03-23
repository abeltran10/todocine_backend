package com.todocine;


import com.todocine.dao.GanadorDAO;
import com.todocine.dao.PremioDAO;
import com.todocine.dto.GanadorDTO;
import com.todocine.dto.PremioDTO;
import com.todocine.entities.*;
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

    @Mock
    GanadorDAO ganadorDAO;

    @InjectMocks
    PremioServiceImpl premioService;

    static Premio premio;

    static Ganador ganador;

    @BeforeAll
    static void setUp() {
        Movie movie1 = new Movie("123");
        movie1.setTitle("La sociedad de la nieve");
        movie1.setVotosTC(new ArrayList<>());


        Categoria categoria2 = new Categoria();
        categoria2.setNombre("Mejor Director");

        Premio premio1 = new Premio(1L);
        premio1.setTitulo("Goya");

        premio = premio1;

        Ganador ganador1 = new Ganador();
        GanadorId ganadorId = new GanadorId(premio1, categoria2, movie1, 2024);
        ganador1.setId(ganadorId);

        ganador = ganador1;
    }

    @Test
    void getPremioByCodigo() {
        LOG.info("getPremioByCodigo");

        PremioDTO premioDTO = new PremioDTO(premio);

        Mockito.when(premioDAO.findByCodigo(1)).thenReturn(premio);
        Mockito.when(ganadorDAO.findByIdPremioIdAndIdAnyo(1L,2024)).thenReturn(Arrays.asList(ganador));

        List<GanadorDTO> ganadores = premioService.getPremioByCodigoAnyo(1, 2024);

        assertEquals("Mejor Director", ganadores.get(0).getCategoria());
    }

}
