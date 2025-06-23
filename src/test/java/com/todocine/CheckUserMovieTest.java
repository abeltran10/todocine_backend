package com.todocine;

import com.todocine.dao.GanadorDAO;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
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

    @Autowired
    private GanadorDAO ganadorDAO;

    private MovieDTO movieDTO;

    private Usuario usuario;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void before() {
        ganadorDAO.deleteAll();
        usuarioMovieDAO.deleteAll();
        usuarioDAO.deleteAll();
        movieDAO.deleteAll();

        usuario = new Usuario("test", "1234");

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

    }

    @Test
    void findUserFavs() {
        LOG.info("findUserFavs");

        try {
            mockMvc.perform(get("/usuario/" + usuario.getId() + "/movie?vista=&votada=&page=1")
                            .with(authentication(new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(), usuario.getAuthorities()))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.results").isNotEmpty())
                    .andExpect(jsonPath("$.results[0].id").value("906126"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
