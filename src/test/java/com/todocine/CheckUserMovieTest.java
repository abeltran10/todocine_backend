package com.todocine;

import com.todocine.dao.UsuarioMovieDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.MovieDetailDTO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.entities.UsuarioMovie;
import com.todocine.entities.UserMovieId;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import com.todocine.service.UsuarioMovieService;
import com.todocine.utils.Paginator;
import com.todocine.utils.mapper.MovieMapper;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
public class CheckUserMovieTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckUserMovieTest.class);

    @Autowired
    private UsuarioMovieService usuarioMovieService;

    @Autowired
    private UsuarioMovieDAO usuarioMovieDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private MovieDAO movieDAO;

    private MovieDTO movieDTO;

    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void before() {
        usuarioMovieDAO.deleteAll();
        usuarioDAO.deleteAll();
        movieDAO.deleteAll();

        Usuario usuario = new Usuario("test", "1234");

        List<Usuario> usuarioList = Arrays.asList(usuario);

        Movie movie = new Movie("906126", "La sociedad de la nieve"
                , "La sociedad de la nieve", "/9tkJPQb4X4VoU3S5nqLDohZijPj.jpg"
                , "El 13 de octubre de 1972, el vuelo 571 de la Fuerza Aérea Uruguaya, fl…", "2023-12-13"
                , 1284.858, 467, 8.158, "es",  0, 0D);
        UsuarioMovie usuarioMovie = new UsuarioMovie(new UserMovieId(usuario, movie));
        usuarioMovie.setVista("N");
        usuarioMovie.setFavoritos("S");
        usuarioMovie.setVoto(null);

        movie = movieDAO.save(movie);
        movieDTO = MovieMapper.toDTO(movie);

        usuario = usuarioDAO.save(usuario);

        usuarioMovieDAO.save(usuarioMovie);

        usuarioDTO = UserMapper.toDTO(usuario);

        Authentication authentication = new UsernamePasswordAuthenticationToken(UserMapper.toEntity(usuarioDTO), usuarioDTO.getPassword());
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

    }

    @Test
    void findUserFavs() {
        LOG.info("findUserFavs");

        Paginator<MovieDetailDTO> paginator = usuarioMovieService.getUsuarioFavs(usuarioDTO.getId(), 1);
        assertTrue(paginator != null);
        assertTrue(paginator.getResults().size() == 1);
        assertEquals("906126", paginator.getResults().get(0).getId());
    }

}
