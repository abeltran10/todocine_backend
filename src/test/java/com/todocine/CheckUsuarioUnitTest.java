package com.todocine;

import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.exceptions.BadRequestException;
import com.todocine.model.Movie;
import com.todocine.model.Usuario;
import com.todocine.service.impl.UserServiceImpl;
import com.todocine.utils.Paginator;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CheckUsuarioUnitTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckUsuarioUnitTest.class);


    @Mock
    private UsuarioDAO usuarioDAO;

    @Mock
    private MovieDAO movieDAO;

    @InjectMocks
    private UserServiceImpl usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static Usuario usuario;

    private static Movie movie;

    @BeforeAll
    static void setUp() {
        LOG.info("setUp");

        usuario = new Usuario ("test", "1234");
        usuario.setId("9876");

        movie = new Movie("906126","La sociedad de la nieve"
                , "La sociedad de la nieve", "/9tkJPQb4X4VoU3S5nqLDohZijPj.jpg"
                , "El 13 de octubre de 1972, el vuelo 571 de la Fuerza Aérea Uruguaya, fl…", "2023-12-13"
                , 1284.858, 467, 8.158, new ArrayList<>()
                , "es", new ArrayList<>(), new ArrayList<>(), 0, 0D);

        usuario.setFavoritos(Arrays.asList(movie));
    }

    @Test
    void findUser() {
        LOG.info("findUser");

        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
        usuarioDTO.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Mockito.when(usuarioDAO.findByUsername("test")).thenReturn(usuarioDTO);

        Usuario test = usuarioService.getUsuarioByName("test");
        assertEquals("9876", test.getId());
    }

    @Test
    void createSameUser() {
        LOG.info("createSameUser");

        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
        usuarioDTO.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Mockito.when(usuarioDAO.findByUsername("test")).thenReturn(usuarioDTO);

        try {
            usuarioService.insertUsuario(usuario);
        } catch (BadRequestException ex) {
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

    @Test
    void findUserFavs() {
        LOG.info("findUserFavs");

        MovieDTO movieDTO = new MovieDTO(movie);
        Mockito.when(movieDAO.findByUsuariosId("9876")).thenReturn(Arrays.asList(movieDTO));

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
                , new ArrayList<>(), new ArrayList<>(), 0, 0D);
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
        MovieDTO movieDTO = new MovieDTO(movie);


        Mockito.when(usuarioDAO.findById("9876")).thenReturn(Optional.of(usuarioDTO));
        Mockito.when(movieDAO.findById("572802")).thenReturn(Optional.of(movieDTO));

        Movie movie1 = usuarioService.addFavoritosByUserId(usuario.getId(), movie);

        assertEquals("572802", movie1.getId());

    }

    @Test
    void deleteUserFavs() {
        LOG.info("deleteUserFavs");
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
        MovieDTO movieDTO = new MovieDTO(movie);

        Mockito.when(usuarioDAO.findById("9876")).thenReturn(Optional.of(usuarioDTO));
        Mockito.when(movieDAO.findById("906126")).thenReturn(Optional.of(movieDTO));

        usuarioService.deleteFavoritosByUserId(usuario.getId(), movie.getId());

        assertTrue(usuarioDTO.getFavoritos().isEmpty());
    }
}
