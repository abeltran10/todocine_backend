package com.todocine;

import com.todocine.dao.FavoritosDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.FavoritosDTO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.entities.Favoritos;
import com.todocine.entities.FavoritosId;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import com.todocine.exceptions.BadRequestException;
import com.todocine.service.impl.UserServiceImpl;
import com.todocine.utils.Paginator;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
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

    private static MovieDTO movieDTO;

    @BeforeAll
    static void setUp() {
        LOG.info("setUp");

        usuarioDTO = new UsuarioDTO("test", "1234");
        usuarioDTO.setId(9876L);

        movieDTO = new MovieDTO("906126","La sociedad de la nieve"
                , "La sociedad de la nieve", "/9tkJPQb4X4VoU3S5nqLDohZijPj.jpg"
                , "El 13 de octubre de 1972, el vuelo 571 de la Fuerza Aérea Uruguaya, fl…", "2023-12-13"
                , 1284.858, 467, 8.158, new ArrayList<>(), "es"
                , new ArrayList<>(), new ArrayList<>(), 0, 0D);

        usuarioDTO.setFavoritos(Arrays.asList(new FavoritosDTO(usuarioDTO.getId(), movieDTO.getId())));
    }

    @Test
    void findUser() {
        LOG.info("findUser");

        Usuario usuario = UserMapper.toEntity(usuarioDTO);
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(usuario);

        // Mock del SecurityContext
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        // Setear el contexto de seguridad
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(usuarioDAO.findByUsername("test")).thenReturn(usuario);

        UsuarioDTO test = usuarioService.getUsuarioByName("test");
        assertEquals(9876L, test.getId());
    }

    @Test
    void createSameUser() {
        LOG.info("createSameUser");

        Usuario usuario = UserMapper.toEntity(usuarioDTO);
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        Mockito.when(usuarioDAO.findByUsername("test")).thenReturn(usuario);

        try {
            usuarioService.insertUsuario(usuarioDTO);
        } catch (BadRequestException ex) {
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

        UsuarioDTO usuarioDTO1 = usuarioDTO;
        usuarioDTO1.setPassword("abcd");

        usuarioDTO1 = usuarioService.updateUsuario(usuarioDTO1.getId(), usuarioDTO1);

        assertTrue(passwordEncoder.matches("abcd", usuarioDTO1.getPassword()));
        assertEquals("test", usuarioDTO1.getUsername());

    }


}
