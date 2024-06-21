package com.todocine;

import com.todocine.dao.PremioDAO;
import com.todocine.entities.Categoria;
import com.todocine.entities.Premio;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CheckPremiosTest {

    public static Logger LOG = LoggerFactory.getLogger(CheckPremiosTest.class);

    @Autowired
    private PremioDAO premioDAO;

    @Test
    void getPremioById() {
        Premio premio = premioDAO.findById("65d2269cff4e277756e2195c").orElse(null);

        LOG.info(premio.getCategorias().get(0).getMovie().getOriginalTitle());

        assertEquals(28, premio.getCategorias().size());
    }
}
