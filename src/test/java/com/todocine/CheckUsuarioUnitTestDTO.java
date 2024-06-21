package com.todocine;

import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import com.todocine.exceptions.BadRequestException;
import com.todocine.dto.MovieDTO;
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
public class CheckUsuarioUnitTestDTO {
    public static Logger LOG = LoggerFactory.getLogger(CheckUsuarioUnitTestDTO.class);


    @Mock
    private UsuarioDAO usuarioDAO;

    @Mock
    private MovieDAO movieDAO;

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
        usuarioDTO.setId("9876");

        movieDTO = new MovieDTO("906126","La sociedad de la nieve"
                , "La sociedad de la nieve", "/9tkJPQb4X4VoU3S5nqLDohZijPj.jpg"
                , "El 13 de octubre de 1972, el vuelo 571 de la Fuerza Aérea Uruguaya, fl…", "2023-12-13"
                , 1284.858, 467, 8.158, new ArrayList<>()
                , "es", new ArrayList<>(), new ArrayList<>(), 0, 0D);

        usuarioDTO.setFavoritos(Arrays.asList(movieDTO));
    }

    @Test
    void findUser() {
        LOG.info("findUser");

        Usuario usuario = new Usuario(usuarioDTO);
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        Mockito.when(usuarioDAO.findByUsername("test")).thenReturn(usuario);

        UsuarioDTO test = usuarioService.getUsuarioByName("test");
        assertEquals("9876", test.getId());
    }

    @Test
    void createSameUser() {
        LOG.info("createSameUser");

        Usuario usuario = new Usuario(usuarioDTO);
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

        Usuario usuario = new Usuario(usuarioDTO);
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));

        Mockito.when(usuarioDAO.findById("9876")).thenReturn(Optional.of(usuario));

        UsuarioDTO usuarioDTO1 = usuarioDTO;
        usuarioDTO1.setPassword("abcd");

        usuarioDTO1 = usuarioService.updateUsuario(usuarioDTO1.getId(), usuarioDTO1);

        assertTrue(passwordEncoder.matches("abcd", usuarioDTO1.getPassword()));
        assertEquals("test", usuarioDTO1.getUsername());

    }

    @Test
    void findUserFavs() {
        LOG.info("findUserFavs");

        Movie movie = new Movie(movieDTO);
        Mockito.when(usuarioDAO.findFavoritosById("9876")).thenReturn(Arrays.asList(movie));

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
        Usuario usuario = new Usuario(usuarioDTO);
        Movie movie = new Movie(movieDTO);


        Mockito.when(usuarioDAO.findById("9876")).thenReturn(Optional.of(usuario));
        Mockito.when(movieDAO.findById("572802")).thenReturn(Optional.of(movie));

        MovieDTO movieDTO1 = usuarioService.addFavoritosByUserId(usuarioDTO.getId(), movieDTO);

        assertEquals("572802", movieDTO1.getId());

    }

    @Test
    void deleteUserFavs() {
        LOG.info("deleteUserFavs");
        Usuario usuario = new Usuario(usuarioDTO);
        Movie movie = new Movie(movieDTO);

        Mockito.when(usuarioDAO.findById("9876")).thenReturn(Optional.of(usuario));
        Mockito.when(movieDAO.findById("906126")).thenReturn(Optional.of(movie));

        usuarioService.deleteFavoritosByUserId(usuarioDTO.getId(), movieDTO.getId());

        assertTrue(usuario.getFavoritos().isEmpty());
    }
}
