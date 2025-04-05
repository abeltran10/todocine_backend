package com.todocine.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todocine.dto.UsuarioDTO;
import com.todocine.entities.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static com.todocine.configuration.Constants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authManager = authenticationManager;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            UsuarioDTO credenciales = new ObjectMapper().readValue(request.getInputStream(), UsuarioDTO.class);

            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credenciales.getUsername(),
                            credenciales.getPassword(),
                            new ArrayList<>()));

            return authentication;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws JWTCreationException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Usuario usuario = (Usuario) auth.getPrincipal();
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);

        String token = JWT.create()
                .withSubject(mapper.writeValueAsString(usuarioDTO))
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SUPER_SECRET_KEY));

        response.addHeader(HEADER_AUTHORIZACION_KEY, TOKEN_BEARER_PREFIX + " " + token);
        response.getWriter().write(mapper.writeValueAsString(usuarioDTO));
        response.getWriter().flush();

    }

}
