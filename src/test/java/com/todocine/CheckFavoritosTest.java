package com.todocine;

import com.todocine.dao.FavoritosDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.entities.Favoritos;
import com.todocine.entities.FavoritosId;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import com.todocine.service.FavoritosService;
import com.todocine.utils.Paginator;
import com.todocine.utils.mapper.MovieMapper;
import com.todocine.utils.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ActiveProfiles("test")
public class CheckFavoritosTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckFavoritosTest.class);

    @Autowired
    private FavoritosService favoritosService;

    @Autowired
    private FavoritosDAO favoritosDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private MovieDAO movieDAO;

    private MovieDTO movieDTO;

    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void before() {
        usuarioDAO.deleteAll();
        favoritosDAO.deleteAll();
        movieDAO.deleteAll();

        Usuario usuario = new Usuario("test", "1234");

        List<Usuario> usuarioList = Arrays.asList(usuario);

        Movie movie = new Movie("906126", "La sociedad de la nieve"
                , "La sociedad de la nieve", "/9tkJPQb4X4VoU3S5nqLDohZijPj.jpg"
                , "El 13 de octubre de 1972, el vuelo 571 de la Fuerza Aérea Uruguaya, fl…", "2023-12-13"
                , 1284.858, 467, 8.158, "es", new ArrayList<>(), 0, 0D);
        Favoritos favorito = new Favoritos(new FavoritosId(usuario, movie));

        movie = movieDAO.save(movie);
        movieDTO = MovieMapper.toDTO(movie);

        usuario.setFavoritos(new ArrayList<>());
        usuario.getFavoritos().add(favorito);
        usuario = usuarioDAO.save(usuario);

        favoritosDAO.save(favorito);

        usuarioDTO = UserMapper.toDTO(usuario);

        Authentication authentication = new UsernamePasswordAuthenticationToken(UserMapper.toEntity(usuarioDTO), usuarioDTO.getPassword());
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

    }

    @Test
    void findUserFavs() {
        LOG.info("findUserFavs");

        Paginator<MovieDTO> paginator = favoritosService.getUsuarioFavs(1);
        assertTrue(paginator != null);
        assertTrue(paginator.getResults().size() == 1);
        assertEquals("906126", paginator.getResults().get(0).getId());
    }

    @Test
    void addFavoritos() {
        LOG.info("addFavoritosByUserId");

        MovieDTO movieDTO = new MovieDTO("572802", "Aquaman and the Lost Kingdom", "Aquaman y el reino perdido"
                , "/d9Hv3b37ZErby79f4iqTZ8doaTp.jpg", "overview", "2023-12-20",1112.367,449, 6.482, new ArrayList<>(), "en"
                , new ArrayList<>(), new ArrayList<>(), 0, 0D);

        MovieDTO movieDTO1 = favoritosService.addFavoritos(movieDTO);

        assertEquals("572802", movieDTO1.getId());

    }

    @Test
    @Transactional
    void deleteFavs() {
        LOG.info("deleteUserFavs");

        favoritosService.deleteFavoritos(movieDTO.getId());

        Usuario usuario = usuarioDAO.findByUsername(usuarioDTO.getUsername());

        assertTrue(usuario.getFavoritos().isEmpty());
    }
}
