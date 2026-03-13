package com.todocine;


import com.todocine.dao.GanadorDAO;
import com.todocine.dao.PremioDAO;
import com.todocine.dto.response.GanadorDTO;
import com.todocine.entities.*;
import com.todocine.service.impl.GanadorServiceImpl;
import com.todocine.utils.Paginator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckGanadorUnitarioTest {

    public static Logger LOG = LoggerFactory.getLogger(CheckGanadorUnitarioTest.class);

    @Mock
    PremioDAO premioDAO;

    @Mock
    GanadorDAO ganadorDAO;

    @InjectMocks
    GanadorServiceImpl premioService;

    static Ganador ganador;

    @BeforeAll
    static void setUp() {
        Movie movie1 = new Movie(123L);
        movie1.setTitle("La sociedad de la nieve");


        Categoria categoria2 = new Categoria();
        categoria2.setNombre("Mejor Director");

        Premio premio1 = new Premio(1L);
        premio1.setTitulo("Goya");

        CategoriaPremio categoriaPremio = new CategoriaPremio();
        CategoriaPremioId categoriaPremioId = new CategoriaPremioId(categoria2, premio1);
        categoriaPremio.setId(categoriaPremioId);

        Ganador ganador1 = new Ganador();
        GanadorId ganadorId = new GanadorId(categoriaPremio, movie1, 2024);
        ganador1.setId(ganadorId);

        ganador = ganador1;
    }

    @Test
    void getPremioByCodigo() {
        LOG.info("getPremioByCodigo");

        Paginator<GanadorDTO> paginator = new Paginator<>();
        Pageable pageable = PageRequest.of(0, 21);
        Page<Ganador> page = new PageImpl<>(Arrays.asList(ganador));


        Mockito.when(ganadorDAO.findById_CategoriaPremio_Id_Premio_IdAndId_Anyo(1L,2024, pageable)).thenReturn(page);

        Paginator<GanadorDTO> ganadores = premioService.getGanadoresByPremioIdAnyo(1L, 2024, 1);

        assertEquals("Mejor Director", ganadores.getResults().get(0).getCategoria());
    }

}
