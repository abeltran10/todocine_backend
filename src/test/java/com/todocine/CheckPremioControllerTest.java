package com.todocine;

import com.todocine.controller.PremioController;
import com.todocine.dto.PremioDTO;
import com.todocine.exceptions.NotFoudException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CheckPremioControllerTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckPremioControllerTest.class);

    @Autowired
    private PremioController premioController;

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
