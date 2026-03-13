package com.todocine;

import com.todocine.dao.*;
import com.todocine.dto.response.MovieDTO;
import com.todocine.entities.*;
import com.todocine.service.TMDBService;
import com.todocine.utils.mapper.MovieMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckGanadorTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckGanadorTest.class);

    @Autowired
    private PremioDAO premioDAO;

    @Autowired
    private CategoriaDAO categoriaDAO;

    @Autowired
    private CategoriaPremioDAO categoriaPremioDAO;

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private TMDBService tmdbService;

    @Autowired
    private UsuarioMovieDAO favoritosDAO;

    @Autowired
    private GanadorDAO ganadorDAO;

    private Long premioId;

    @Autowired
    private UsuarioDAO usuarioDAO;

    private Usuario usuario;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    void setUp() {
        ganadorDAO.deleteAll();
        categoriaPremioDAO.deleteAll();
        categoriaDAO.deleteAll();
        premioDAO.deleteAll();
        favoritosDAO.deleteAll();
        movieDAO.deleteAll();

        Movie movie = null;

        try {
            MovieDTO movieDTO = MovieMapper.toDTO(tmdbService.getMovieById(906126L));
            movie = MovieMapper.toEntity(movieDTO);
            movieDAO.save(movie);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Premio premio = new Premio(null, "Goya");
        premioDAO.save(premio);
        premioId = premio.getId();

        Categoria categoria = new Categoria(null, "Mejor película");
        categoriaDAO.save(categoria);

        CategoriaPremio categoriaPremio = new CategoriaPremio();
        CategoriaPremioId categoriaPremioId = new CategoriaPremioId(categoria, premio);
        categoriaPremio.setId(categoriaPremioId);
        categoriaPremioDAO.save(categoriaPremio);

        GanadorId ganadorId = new GanadorId(categoriaPremio, movie, 2024);
        Ganador ganador = new Ganador();
        ganador.setId(ganadorId);

        ganadorDAO.save(ganador);

        usuarioDAO.deleteAll();

        usuario = new Usuario("test", "1234");
        usuario.setRol("USUARIO");
        usuarioDAO.save(usuario);


    }

    @Test
    void getGanador() {

        try {
            mockMvc.perform(get("/ganadores?premioId=" + premioId +"&anyo=2024&pagina=1")
                            .with(authentication(new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(), usuario.getAuthorities()))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.results[0].title").value("La sociedad de la nieve"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getPremioByCodigoException() {
        try {
            mockMvc.perform(get("/ganadores?premioId=0&anyo=2023&pagina=1")
                            .with(authentication(new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(), usuario.getAuthorities()))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.total_results").value("0"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }

}
