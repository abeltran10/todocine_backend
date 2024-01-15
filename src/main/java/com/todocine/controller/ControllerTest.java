package com.todocine.controller;

import com.todocine.dao.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Profile("test")
@RequestMapping("/testing")
public class ControllerTest {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @PostMapping("/reset")
    public void resetDatabase() {
        usuarioDAO.deleteAll();
    }

}
