package com.todocine.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

import static com.todocine.configuration.Constants.*;

public class JWTAuthorisationFilter extends BasicAuthenticationFilter {

    public JWTAuthorisationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_AUTHORIZACION_KEY);
        if (header == null || !header.startsWith(TOKEN_BEARER_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JWTVerificationException ex) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }


        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws JWTVerificationException {
        ObjectMapper mapper = new ObjectMapper();
        String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
        token = token.substring(token.indexOf(" ") + 1);

        if (!token.isEmpty()) {
            // Se procesa el token y se recupera el usuario.
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SUPER_SECRET_KEY))
                    .build(); //Reusable verifier instance
            String usuarioString = verifier.verify(token).getSubject();

            try {
                UsuarioDTO usuarioDTO = mapper.readValue(usuarioString, UsuarioDTO.class);
                Usuario usuario = new Usuario(usuarioDTO);

                if (usuario != null) {
                    return new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(), new ArrayList<>());
                }
            } catch (JsonProcessingException e) {
                throw new JWTVerificationException(e.getMessage());
            }

            return null;
        }
        return null;
    }

}
