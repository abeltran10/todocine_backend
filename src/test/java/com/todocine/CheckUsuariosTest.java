package com.todocine;

import com.todocine.dao.UsuarioDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CheckUsuariosTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckUsuariosTest.class);

    @Autowired
    private UsuarioDAO usuarioDAO;
}
