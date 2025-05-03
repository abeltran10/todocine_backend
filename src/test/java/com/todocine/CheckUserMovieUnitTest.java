package com.todocine;

import com.todocine.dao.UsuarioMovieDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.MovieDetailDTO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.entities.*;
import com.todocine.exceptions.BadRequestException;
import com.todocine.service.impl.UsuarioMovieServiceImpl;
import com.todocine.utils.Paginator;
import com.todocine.utils.mapper.MovieMapper;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckUserMovieUnitTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckUserMovieUnitTest.class);

    @Mock
    private UsuarioMovieDAO usuarioMovieDAO;

    @Mock
    private MovieDAO movieDAO;

    @Mock
    private UsuarioDAO usuarioDAO;

    @InjectMocks
    private UsuarioMovieServiceImpl usuarioMovieService;

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
                , new ArrayList<>(), 0, 0D);

    }

    @Test
    void findUserFavs() {
        LOG.info("findUserFavs");

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(UserMapper.toEntity(usuarioDTO));

        // Mock del SecurityContext
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        // Setear el contexto de seguridad
        SecurityContextHolder.setContext(securityContext);

        Movie movie = MovieMapper.toEntity(movieDTO);
        Pageable pageable = PageRequest.of(0, 21);

        UsuarioMovie usuarioMovie = new UsuarioMovie(new UserMovieId(new Usuario(usuarioDTO.getId()), movie));
        usuarioMovie.setVista("N");
        usuarioMovie.setFavoritos("S");
        usuarioMovie.setVoto(null);

        Page<UsuarioMovie> usuarioMovies = new PageImpl<>(Arrays.asList(usuarioMovie));

        Mockito.when(usuarioMovieDAO.findByIdUsuarioIdAndFavoritos(9876L, "S", pageable)).thenReturn(usuarioMovies);
        Mockito.when(movieDAO.findById(movie.getId())).thenReturn(Optional.of(movie));

        Paginator<MovieDetailDTO> paginator = usuarioMovieService.getUsuarioFavs(usuarioDTO.getId(), 1);
        assertNotNull(paginator);
        assertEquals(1, paginator.getResults().size());
        assertEquals("906126", paginator.getResults().get(0).getId());
    }

}
