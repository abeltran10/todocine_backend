package com.todocine;

import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.model.Usuario;
import com.todocine.service.UsuarioService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Usuario usuario;

    @BeforeEach
    void clearDatabase() {
        LOG.info("clearDatabase");
        usuarioDAO.deleteAll();

        UsuarioDTO usuarioDTO = new UsuarioDTO("test","1234");
        usuarioDTO = usuarioDAO.save(usuarioDTO);
        usuario = new Usuario(usuarioDTO);
    }

    @Test
    void findUser() {
        LOG.info("findUser");
        UsuarioDTO test = usuarioDAO.findByUsername("test");
        assertThat(test.getId()).isEqualTo(usuario.getId());
    }

    @Test
    void createSameUser() {
        LOG.info("createSameUser");
        Usuario usuario = new Usuario("test", "1234");

        try {
            Usuario test = usuarioService.insertUsuario(usuario);
        } catch (ResponseStatusException ex) {
            LOG.info(ex.getMessage());
            assertTrue(ex.getMessage().contains("Un usuario con ese nombre ya existe"));
        }
    }

    @Test
    void updateUser() {

        Usuario usuario1 = new Usuario("test", "abcd");
        usuario1 = usuarioService.updateUsuario(usuario.getId(), usuario1);

        assertTrue(passwordEncoder.matches("abcd", usuario1.getPassword()));
        assertTrue(usuario1.getUsername().equals("test"));
        assertTrue(usuario1.getId().equals(usuario.getId()));
    }
}
