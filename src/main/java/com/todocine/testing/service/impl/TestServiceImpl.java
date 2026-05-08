package com.todocine.testing.service.impl;

import com.todocine.dao.UsuarioDAO;
import com.todocine.testing.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Override
    public void deleteAll() {
        usuarioDAO.deleteAll();
    }
}
