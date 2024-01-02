package com.todocine;


import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.UsuarioDTO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CheckUsuario {
    public static Logger LOG = LoggerFactory.getLogger(CheckUsuario.class);

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Test
    void getUsuarioByName() {
        List<UsuarioDTO> usuarioDTOS = usuarioDAO.findByUsername("user1234");

        LOG.info(usuarioDTOS.toString());

        assertTrue (usuarioDTOS.get(0).getUsername().equals("user1234"));

    }
}
