package com.todocine;


import com.todocine.dao.GanadorDAO;
import com.todocine.dao.PremioDAO;
import com.todocine.dto.GanadorDTO;
import com.todocine.dto.PremioDTO;
import com.todocine.entities.*;
import com.todocine.service.impl.PremioServiceImpl;
import com.todocine.utils.Paginator;
import com.todocine.utils.mapper.PremioMapper;
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

        PremioDTO premioDTO = PremioMapper.toDTO(premio);

        Paginator<GanadorDTO> paginator = new Paginator<>();
        Pageable pageable = PageRequest.of(0, 21);
        Page<Ganador> page = new PageImpl<>(Arrays.asList(ganador));


        Mockito.when(ganadorDAO.findByIdPremioIdAndIdAnyo(1L,2024, pageable)).thenReturn(page);

        Paginator<GanadorDTO> ganadores = premioService.getPremioByCodigoAnyo(1L, 2024, 1);

        assertEquals("Mejor Director", ganadores.getResults().get(0).getCategoria());
    }

}
