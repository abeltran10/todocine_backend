package com.todocine;

import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.model.Usuario;
import com.todocine.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CheckUsuarioUnitTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckUsuarioUnitTest.class);


    @Mock
    private UsuarioDAO usuarioDAO;

    @InjectMocks
    private UserServiceImpl usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static Usuario usuario;

    @BeforeAll
    static void setUp() {
        LOG.info("setUp");

        usuario = new Usuario ("test", "1234");
        usuario.setId("9876");

    }

    @Test
    void findUser() {
        LOG.info("findUser");

        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
        usuarioDTO.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Mockito.when(usuarioDAO.findByUsername("test")).thenReturn(usuarioDTO);

        Usuario test = usuarioService.getUsuarioByName("test");
        assertEquals(usuarioDTO.getId(), test.getId());
    }

    @Test
    void createSameUser() {
        LOG.info("createSameUser");

        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
        usuarioDTO.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Mockito.when(usuarioDAO.findByUsername("test")).thenReturn(usuarioDTO);

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

        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
        usuarioDTO.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Mockito.when(usuarioDAO.findById("9876")).thenReturn(Optional.of(usuarioDTO));

        Usuario usuario1 = usuario;
        usuario1.setPassword("abcd");

        usuario1 = usuarioService.updateUsuario(usuario1.getId(), usuario1);

        assertTrue(passwordEncoder.matches("abcd", usuario1.getPassword()));
        assertEquals("test", usuario1.getUsername());

    }
}
