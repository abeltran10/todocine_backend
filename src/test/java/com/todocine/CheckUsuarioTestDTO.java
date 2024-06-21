package com.todocine;

import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.UsuarioDTO;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CheckUsuarioTestDTO {
    public static Logger LOG = LoggerFactory.getLogger(CheckUsuarioTestDTO.class);
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

    private UsuarioDTO usuarioDTO;

    private MovieDTO movieDTO;

    @BeforeEach
    void before() {
        usuarioDAO.deleteAll();
        movieDAO.deleteAll();

        Usuario usuario = new Usuario("test", "1234");
        usuarioDAO.save(usuario);

        List<Usuario> usuarioList = Arrays.asList(usuario);

        Movie movie = new Movie("906126","La sociedad de la nieve"
                , "La sociedad de la nieve", "/9tkJPQb4X4VoU3S5nqLDohZijPj.jpg"
                , "El 13 de octubre de 1972, el vuelo 571 de la Fuerza Aérea Uruguaya, fl…", "2023-12-13"
                , 1284.858, 467, 8.158, new ArrayList<>()
                , "es", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0, 0D) ;
        List<Movie> favoritos = Arrays.asList(movie);

        movie = movieDAO.save(movie);
        movieDTO = new MovieDTO(movie);

        usuario.setFavoritos(favoritos);
        usuario = usuarioDAO.save(usuario);

        usuarioDTO = new UsuarioDTO(usuario);
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
                , "/d9Hv3b37ZErby79f4iqTZ8doaTp.jpg"
                , "Al no poder derrotar a Aquaman la primera vez, Black Manta, todavía impulsado por la necesidad de vengar " +
                "la muerte de su padre, no se detendrá ante nada para derrotar a Aquaman de una vez por todas. " +
                "Esta vez Black Manta es más formidable que nunca y ejerce el poder del mítico Tridente Negro, que desata " +
                "una fuerza antigua y malévola.  Para derrotarlo, Aquaman recurrirá a su hermano encarcelado Orm, " +
                "el ex rey de la Atlántida, para forjar una alianza improbable. Juntos, deben dejar de lado sus diferencias" +
                " para proteger su reino y salvar a la familia de Aquaman, y al mundo, de una destrucción irreversible."
                , "2023-12-20",1112.367,449, 6.482, new ArrayList<>(), "en"
                , new ArrayList<>(), new ArrayList<>(), 0, 0D);

        MovieDTO movieDTO1 = usuarioService.addFavoritosByUserId(usuarioDTO.getId(), movieDTO);

        assertEquals("572802", movieDTO1.getId());

    }

    @Test
    void deleteUserFavs() {
        LOG.info("deleteUserFavs");

        usuarioService.deleteFavoritosByUserId(usuarioDTO.getId(), movieDTO.getId());

        Usuario usuario = usuarioDAO.findByUsername(usuarioDTO.getUsername());

        assertTrue(usuario.getFavoritos().isEmpty());
    }
}
