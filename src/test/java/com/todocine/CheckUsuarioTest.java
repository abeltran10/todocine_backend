package com.todocine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todocine.dao.GanadorDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dao.UsuarioMovieDAO;
import com.todocine.dto.request.UsuarioReqDTO;
import com.todocine.dto.response.UsuarioDTO;
import com.todocine.entities.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckUsuarioTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckUsuarioTest.class);

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private GanadorDAO ganadorDAO;

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private UsuarioMovieDAO usuarioMovieDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Usuario usuario;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    void setUp() {
        usuarioMovieDAO.deleteAll();
        usuarioDAO.deleteAll();
        ganadorDAO.deleteAll();
        movieDAO.deleteAll();
    }

    @BeforeEach
    void before() {
        usuarioDAO.deleteAll();

        usuario = new Usuario("test", "1234");
        usuario.setRol("USUARIO");
        usuario = usuarioDAO.save(usuario);
    }

    @Test
    void addUsuario() {
        LOG.info("addUsuario");

        UsuarioReqDTO usuarioReqDTO = new UsuarioReqDTO("test2", "1234");

        try {
            mockMvc.perform(post("/usuarios")
                            .content(objectMapper.writeValueAsString(usuarioReqDTO))             // Body JSON
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(authentication(new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(), usuario.getAuthorities()))))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.username").value(usuarioReqDTO.getUsername()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createSameUser() {
        LOG.info("createSameUser");

        UsuarioReqDTO usuarioReqDTO = new UsuarioReqDTO(usuario.getUsername(), usuario.getPassword());

        try {
            mockMvc.perform(post("/usuarios")
                            .content(objectMapper.writeValueAsString(usuarioReqDTO))             // Body JSON
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(authentication(new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(), usuario.getAuthorities()))))
                    .andExpect(status().isConflict());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateUser() {
        LOG.info("updateUser");

        UsuarioReqDTO usuarioReqDTO = new UsuarioReqDTO(usuario.getUsername(), "abcd");
        usuarioReqDTO.setId(usuario.getId());

        try {
            MvcResult result = mockMvc.perform(put("/usuarios/" + usuarioReqDTO.getId())
                            .content(objectMapper.writeValueAsString(usuarioReqDTO))
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(authentication(new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(), usuario.getAuthorities()))))
                    .andExpect(status().isOk())
                    .andReturn();

            String json = result.getResponse().getContentAsString();
            UsuarioDTO usuarioDTO1 = objectMapper.readValue(json, UsuarioDTO.class);

            assertEquals("test", usuarioDTO1.getUsername());
            assertEquals("USUARIO", usuarioDTO1.getRol());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
