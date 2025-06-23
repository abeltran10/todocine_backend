package com.todocine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todocine.dao.CategoriaDAO;
import com.todocine.dao.UsuarioMovieDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.entities.Usuario;
import com.todocine.exceptions.BadRequestException;
import com.todocine.service.MovieService;
import com.todocine.service.UsuarioService;
import com.todocine.utils.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CheckUsuarioTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckUsuarioTest.class);

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private CategoriaDAO categoriaDAO;

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private UsuarioMovieDAO favoritosDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Usuario usuario;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void before() {
        favoritosDAO.deleteAll();
        usuarioDAO.deleteAll();
        movieDAO.deleteAll();

        usuario = new Usuario("test", "1234");

        usuario = usuarioDAO.save(usuario);
    }

    @Test
    void findUser() {
        LOG.info("findUser");

        try {
            mockMvc.perform(get("/usuario/username/test")
                            .with(authentication(new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(), usuario.getAuthorities()))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(usuario.getId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addUsuario() {
        LOG.info("addUsuario");

        UsuarioDTO usuarioDTO = new UsuarioDTO("test2", "1234");

        try {
            mockMvc.perform(post("/usuario")
                            .content(objectMapper.writeValueAsString(usuarioDTO))             // Body JSON
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(authentication(new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(), usuario.getAuthorities()))))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.username").value(usuarioDTO.getUsername()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createSameUser() {
        LOG.info("createSameUser");

        UsuarioDTO usuarioDTO = UserMapper.toDTO(usuario);

        try {
            mockMvc.perform(post("/usuario")
                            .content(objectMapper.writeValueAsString(usuarioDTO))             // Body JSON
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(authentication(new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(), usuario.getAuthorities()))))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateUser() {
        LOG.info("updateUser");

        UsuarioDTO usuarioDTO = UserMapper.toDTO(usuario);
        usuarioDTO.setPassword("abcd");

        try {
            MvcResult result = mockMvc.perform(put("/usuario/" + usuarioDTO.getId())
                            .content(objectMapper.writeValueAsString(usuarioDTO))
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(authentication(new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(), usuario.getAuthorities()))))
                    .andExpect(status().isOk())
                    .andReturn();

            String json = result.getResponse().getContentAsString();
            UsuarioDTO usuarioDTO1 = objectMapper.readValue(json, UsuarioDTO.class);

            assertTrue(passwordEncoder.matches("abcd", usuarioDTO1.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
