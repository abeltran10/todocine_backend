package com.todocine;

import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.model.Usuario;
import com.todocine.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CheckUsuariosTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckUsuariosTest.class);

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private UsuarioService usuarioService;

    @BeforeEach
    @Test
    void cleanDatabase() {
        usuarioDAO.deleteAll();
    }

    @Test
    void createUser() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("test","1234");

        usuarioDAO.save(usuarioDTO);

        UsuarioDTO test = usuarioDAO.findByUsername("test");

        assertThat(test).isNotNull();

    }

    @Test
    void createSameUser() {
        Usuario usuario = new Usuario("test", "1234");

        try {
            Usuario test = usuarioService.insertUsuario(usuario);
        } catch (ResponseStatusException ex) {
            assertTrue(ex.getMessage().equals("Un usuario con ese nombre ya existe"));
        }

    }
}
