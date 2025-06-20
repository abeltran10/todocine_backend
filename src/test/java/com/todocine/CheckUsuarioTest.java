package com.todocine;

import com.todocine.dao.CategoriaDAO;
import com.todocine.dao.UsuarioMovieDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.entities.Usuario;
import com.todocine.exceptions.BadRequestException;
import com.todocine.service.MovieService;
import com.todocine.service.UsuarioService;
import com.todocine.utils.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class CheckUsuarioTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckUsuarioTest.class);
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private CategoriaDAO categoriaDAO;

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private UsuarioMovieDAO favoritosDAO;

    @Autowired
    private MovieService movieService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UsuarioDTO usuarioDTO;


    @BeforeEach
    void before() {
        favoritosDAO.deleteAll();
        usuarioDAO.deleteAll();
        movieDAO.deleteAll();

        Usuario usuario = new Usuario("test", "1234");

        List<Usuario> usuarioList = Arrays.asList(usuario);

       /* Movie movie = new Movie("906126","La sociedad de la nieve"
                , "La sociedad de la nieve", "/9tkJPQb4X4VoU3S5nqLDohZijPj.jpg"
                , "El 13 de octubre de 1972, el vuelo 571 de la Fuerza Aérea Uruguaya, fl…", "2023-12-13"
                , 1284.858, 467, 8.158, "es", 0, 0D) ;
        Favoritos favorito = new Favoritos(new FavoritosId(usuario,movie));

        movie = movieDAO.save(movie);
        movieDTO = MovieMapper.toDTO(movie);*/

        usuario = usuarioDAO.save(usuario);

        usuarioDTO = UserMapper.toDTO(usuario);

        Authentication authentication = new UsernamePasswordAuthenticationToken(UserMapper.toEntity(usuarioDTO), usuarioDTO.getPassword());
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void findUser() {
        LOG.info("findUser");
        UsuarioDTO test = usuarioService.getUsuarioByName("test");
        assertEquals(usuarioDTO.getId(), test.getId());
    }

    @Test
    void addUsuario() throws BadRequestException {
        LOG.info("addUsuario");
        UsuarioDTO usuarioDTO1 = new UsuarioDTO("test2", "1234");
        usuarioDTO1 = usuarioService.insertUsuario(usuarioDTO1);
        assertTrue(usuarioDTO1.getId() != null);
    }

    @Test
    void createSameUser() {
        LOG.info("createSameUser");

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

        usuarioDTO.setPassword("abcd");
        UsuarioDTO usuarioDTO1 = usuarioService.updateUsuario(usuarioDTO.getId(), usuarioDTO);

        assertTrue(passwordEncoder.matches("abcd", usuarioDTO1.getPassword()));
        assertEquals("test", usuarioDTO1.getUsername());
    }
}
