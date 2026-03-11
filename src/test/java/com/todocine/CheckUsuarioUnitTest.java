package com.todocine;

import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.dto.UsuarioReqDTO;
import com.todocine.entities.Usuario;
import com.todocine.exceptions.ConflictException;
import com.todocine.service.impl.UserServiceImpl;
import com.todocine.utils.mapper.UserMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckUsuarioUnitTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckUsuarioUnitTest.class);


    @Mock
    private UsuarioDAO usuarioDAO;

    @InjectMocks
    private UserServiceImpl usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static UsuarioDTO usuarioDTO;

    @BeforeAll
    static void setUp() {
        LOG.info("setUp");

        usuarioDTO = new UsuarioDTO("test", "1234");
        usuarioDTO.setId(9876L);
    }

    @Test
    void createSameUser() {
        LOG.info("createSameUser");

        Usuario usuario = UserMapper.toEntity(usuarioDTO);
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));

        UsuarioReqDTO usuarioReqDTO = new UsuarioReqDTO(usuarioDTO.getUsername(), usuarioDTO.getPassword());

        Mockito.when(usuarioDAO.findByUsername("test")).thenReturn(usuario);



        try {
            usuarioService.insertUsuario(usuarioReqDTO);
        } catch (ConflictException ex) {
            LOG.info(ex.getMessage());
            assertTrue(ex.getMessage().contains("Un usuario con ese nombre ya existe"));
        }
    }

    @Test
    void updateUser() {
        LOG.info("updateUser");

        Usuario usuario = UserMapper.toEntity(usuarioDTO);
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(usuario);

        // Mock del SecurityContext
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        // Setear el contexto de seguridad
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(usuarioDAO.findById(9876L)).thenReturn(Optional.of(usuario));

        UsuarioReqDTO usuarioReqDTO = new UsuarioReqDTO(usuarioDTO.getUsername(), "abcd");
        usuarioReqDTO.setId(usuarioDTO.getId());

        UsuarioDTO usuarioDTO1 = usuarioService.updateUsuario(usuarioReqDTO.getId(), usuarioReqDTO);

        assertEquals("test", usuarioDTO.getUsername());

    }


}
