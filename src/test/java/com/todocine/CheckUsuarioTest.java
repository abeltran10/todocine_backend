package com.todocine;

import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.model.Usuario;
import com.todocine.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CheckUsuarioTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckUsuarioTest.class);
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Usuario usuario;

    @BeforeEach
    void before() {
        usuarioDAO.deleteAll();

        UsuarioDTO usuarioDTO = new UsuarioDTO("test", "1234");
        usuarioDTO = usuarioDAO.save(usuarioDTO);

        usuario = new Usuario(usuarioDTO);
    }

    @Test
    void findUser() {
        LOG.info("findUser");

        Usuario test = usuarioService.getUsuarioByName("test");
        assertEquals(usuario.getId(), test.getId());
    }

    @Test
    void addUsuario() {
        LOG.info("addUsuario");

        Usuario usuario1 = new Usuario("test2", "1234");

        usuario1 = usuarioService.insertUsuario(usuario1);

        assertTrue(usuario1.getId() != null);
    }

    @Test
    void createSameUser() {
        LOG.info("createSameUser");

        try {
            usuarioService.insertUsuario(usuario);
        } catch (ResponseStatusException ex) {
            LOG.info(ex.getMessage());
            assertTrue(ex.getMessage().contains("Un usuario con ese nombre ya existe"));
        }
    }

    @Test
    void updateUser() {
        LOG.info("updateUser");
        usuario.setPassword("abcd");
        Usuario usuario1 = usuarioService.updateUsuario(usuario.getId(), usuario);

        assertTrue(passwordEncoder.matches("abcd", usuario1.getPassword()));
        assertEquals("test", usuario1.getUsername());


    }


}
