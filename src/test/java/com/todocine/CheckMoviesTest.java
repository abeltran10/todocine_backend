package com.todocine;

import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dao.VotoDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.VotoDTO;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import com.todocine.entities.Voto;
import com.todocine.entities.VotoId;
import com.todocine.service.MovieService;
import com.todocine.utils.Paginator;
import com.todocine.utils.mapper.MovieMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckMoviesTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckMoviesTest.class);

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private VotoDAO votoDAO;

    @Autowired
    private MovieService movieService;

    private Usuario usuario;

    @BeforeAll
    void setUp() {
        usuarioDAO.deleteAll();

        usuario = new Usuario("test", "1234");
        usuarioDAO.save(usuario);

        MovieDTO movieDTO = movieService.getMovieById("906126");
        Movie movie = MovieMapper.toEntity(movieDTO);
        movieDAO.save(movie);

    }

    @Test
    void findMovieById() {
        LOG.info("findMovieById");

        MovieDTO movieDTO = movieService.getMovieById("906126");

        assertEquals("906126", movieDTO.getId());
    }

    @Test
    void findMovieByName() {
        LOG.info("findMovieByName");

        Paginator<MovieDTO> paginator = movieService.getMovieByName("star wars", 1);

        assertTrue(paginator.getResults() != null);
    }

    @Test
    void findMoviesPlayingNow() {
        LOG.info("findMoviesPlayingNow");

        Paginator<MovieDTO> paginator = movieService.getMoviesPlayingNow("ES", 1);

        assertTrue(paginator.getResults() != null);
    }

    @Test
    void addVote() {
        LOG.info("addVote");

        VotoDTO votoDTO = new VotoDTO(usuario.getId(), "906126", 3D);

        MovieDTO movieDTO = movieService.updateVote("906126", usuario.getId(), votoDTO);

        assertEquals(3D, movieDTO.getVotosMediaTC());
    }

    @Test
    void updateVote() {
        LOG.info("updateVote");

        votoDAO.deleteAll();

        Voto voto = new Voto(new VotoId(usuario, new Movie("906126")), 3D);
        votoDAO.save(voto);

        VotoDTO votoDTO = new VotoDTO(usuario.getId(), "906126", 5D);

        MovieDTO movieDTO = movieService.updateVote("906126", usuario.getId(), votoDTO);

        assertEquals(5D, movieDTO.getVotosMediaTC());
    }
}
