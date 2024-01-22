package com.todocine;

import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.model.Movie;
import com.todocine.model.Usuario;
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
import org.springframework.web.server.ResponseStatusException;

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
    private MovieDAO movieDAO;

    @Autowired
    private MovieService movieService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Usuario usuario;

    private Movie movie;

    @BeforeEach
    void before() {
        usuarioDAO.deleteAll();
        movieDAO.deleteAll();

        UsuarioDTO usuarioDTO = new UsuarioDTO("test", "1234");
        usuarioDAO.save(usuarioDTO);

        List<UsuarioDTO> usuarioDTOList = Arrays.asList(usuarioDTO);

        MovieDTO movieDTO = new MovieDTO("906126","La sociedad de la nieve"
                , "La sociedad de la nieve", "/9tkJPQb4X4VoU3S5nqLDohZijPj.jpg"
                , "El 13 de octubre de 1972, el vuelo 571 de la Fuerza Aérea Uruguaya, fl…", "2023-12-13"
                , 1284.858, 467, 8.158, new ArrayList<>()
                , "es", new ArrayList<>(), usuarioDTOList);
        List<MovieDTO> favoritos = Arrays.asList(movieDTO);

        movieDTO = movieDAO.save(movieDTO);
        movie = new Movie(movieDTO);

        usuarioDTO.setFavoritos(favoritos);
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

    @Test
    void findUserFavs() {
        LOG.info("findUserFavs");

        Paginator<Movie> paginator = usuarioService.getUsuarioFavs(usuario.getId(), 1);
        assertTrue(paginator != null);
        assertTrue(paginator.getResults().size() == 1);
        assertEquals("906126", paginator.getResults().get(0).getId());
    }

    @Test
    void addFavoritosByUserId() {
        LOG.info("addFavoritosByUserId");

        Movie movie = new Movie("572802", "Aquaman and the Lost Kingdom", "Aquaman y el reino perdido"
                , "/d9Hv3b37ZErby79f4iqTZ8doaTp.jpg"
                , "Al no poder derrotar a Aquaman la primera vez, Black Manta, todavía impulsado por la necesidad de vengar " +
                "la muerte de su padre, no se detendrá ante nada para derrotar a Aquaman de una vez por todas. " +
                "Esta vez Black Manta es más formidable que nunca y ejerce el poder del mítico Tridente Negro, que desata " +
                "una fuerza antigua y malévola.  Para derrotarlo, Aquaman recurrirá a su hermano encarcelado Orm, " +
                "el ex rey de la Atlántida, para forjar una alianza improbable. Juntos, deben dejar de lado sus diferencias" +
                " para proteger su reino y salvar a la familia de Aquaman, y al mundo, de una destrucción irreversible."
                , "2023-12-20",1112.367,449, 6.482, new ArrayList<>(), "en"
                , new ArrayList<>());

        Usuario usuario1 = usuarioService.addFavoritosByUserId(usuario.getId(), movie);

        assertTrue(usuario1.getFavoritos().size() == 2);

        Movie movie1 = usuario1.getFavoritos().stream().filter(mov -> mov.getId().equals(movie.getId())).findAny().get();

        assertEquals("572802", movie1.getId());

    }

    @Test
    void deleteUserFavs() {
        LOG.info("deleteUserFavs");

        usuarioService.deleteFavoritosByUserId(usuario.getId(), movie.getId());

        UsuarioDTO usuarioDTO = usuarioDAO.findByUsername(usuario.getUsername());

        assertTrue(usuarioDTO.getFavoritos().isEmpty());
    }
}
