package com.todocine;

import com.todocine.dao.*;
import com.todocine.dto.FavoritosDTO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.entities.Favoritos;
import com.todocine.entities.FavoritosId;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import com.todocine.exceptions.BadRequestException;
import com.todocine.dto.MovieDTO;
import com.todocine.service.MovieService;
import com.todocine.service.UsuarioService;
import com.todocine.utils.Paginator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private CategoriaDAO categoriaDAO;

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private FavoritosDAO favoritosDAO;

    @Autowired
    private MovieService movieService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UsuarioDTO usuarioDTO;

    private MovieDTO movieDTO;

    @BeforeEach
    void before() {
        usuarioDAO.deleteAll();
        categoriaDAO.deleteAll();
        movieDAO.deleteAll();

        Usuario usuario = new Usuario("test", "1234");

        List<Usuario> usuarioList = Arrays.asList(usuario);

        Movie movie = new Movie("906126","La sociedad de la nieve"
                , "La sociedad de la nieve", "/9tkJPQb4X4VoU3S5nqLDohZijPj.jpg"
                , "El 13 de octubre de 1972, el vuelo 571 de la Fuerza Aérea Uruguaya, fl…", "2023-12-13"
                , 1284.858, 467, 8.158, "es", new ArrayList<>(), 0, 0D) ;
        Favoritos favorito = new Favoritos(new FavoritosId(usuario,movie));

        movie = movieDAO.save(movie);
        movieDTO = new MovieDTO(movie);

        usuario.setFavoritos(new ArrayList<>());
        usuario.getFavoritos().add(favorito);
        usuario = usuarioDAO.save(usuario);

        favoritosDAO.save(favorito);

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setPassword(usuario.getPassword());
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

    @Test
    void findUserFavs() {
        LOG.info("findUserFavs");

        Paginator<MovieDTO> paginator = usuarioService.getUsuarioFavs(usuarioDTO.getId(), 1);
        assertTrue(paginator != null);
        assertTrue(paginator.getResults().size() == 1);
        assertEquals("906126", paginator.getResults().get(0).getId());
    }

    @Test
    void addFavoritosByUserId() {
        LOG.info("addFavoritosByUserId");

        MovieDTO movieDTO = new MovieDTO("572802", "Aquaman and the Lost Kingdom", "Aquaman y el reino perdido"
                , "/d9Hv3b37ZErby79f4iqTZ8doaTp.jpg", "overview", "2023-12-20",1112.367,449, 6.482, new ArrayList<>(), "en"
                , new ArrayList<>(), new ArrayList<>(), 0, 0D);

        MovieDTO movieDTO1 = usuarioService.addFavoritosByUserId(usuarioDTO.getId(), movieDTO);

        assertEquals("572802", movieDTO1.getId());

    }

    @Test
    @Transactional
    void deleteUserFavs() {
        LOG.info("deleteUserFavs");

        usuarioService.deleteFavoritosByUserId(usuarioDTO.getId(), movieDTO.getId());

        Usuario usuario = usuarioDAO.findByUsername(usuarioDTO.getUsername());

        assertTrue(usuario.getFavoritos().isEmpty());
    }
}
