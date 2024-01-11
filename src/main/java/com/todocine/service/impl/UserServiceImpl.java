package com.todocine.service.impl;

import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.model.Movie;
import com.todocine.model.Usuario;
import com.todocine.service.TMDBService;
import com.todocine.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UsuarioService {

    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private TMDBService tmdbService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);

        UsuarioDTO usuarioDTO = usuarioDAO.findByUsername(username);

        if (usuarioDTO == null)
            throw new UsernameNotFoundException("Usuario o contraseña incorrectos");
        else
            return usuarioDTO;

    }



    @Override
    public Usuario getUsuarioById(String id) throws ResponseStatusException {
        UsuarioDTO usuarioDTO = usuarioDAO.findById(id).get();

        if (usuarioDTO == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario con ese nombre");
        else {
            return new Usuario(usuarioDTO);
        }
    }

    @Override
    public Usuario getUsuarioByName(String username) throws ResponseStatusException {
        log.info("getUsuarioByName");

        UsuarioDTO usuarioDTO = usuarioDAO.findByUsername(username);

        if (usuarioDTO == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario");
        else {
            return new Usuario(usuarioDTO);
        }



    }

    @Override
    public Usuario insertUsuario(Usuario usuario) {
        UsuarioDTO usuarioDTO = usuarioDAO.save(new UsuarioDTO(usuario));

        return new Usuario(usuarioDTO);
    }

    @Override
    public Usuario updateUsuario(String id, Usuario usuario) throws ResponseStatusException {
       log.info("updateUsuario");
        UsuarioDTO usuarioDTO = null;
        try {
            usuarioDTO = usuarioDAO.findById(id).get();
            log.info(usuarioDTO.toString());
            usuarioDTO.setPassword(usuario.getPassword());
            usuarioDTO.setEnabled(usuario.getEnabled());
            usuarioDTO.setAccountNonExpired(usuario.getAccountNonExpired());
            usuarioDTO.setAccountNonLocked(usuario.getAccountNonLocked());
            usuarioDTO.setCredentialsNonExpired(usuario.getCredentialsNonExpired());

            log.info(usuario.getFavoritos().toString());

            List<MovieDTO> movieDTOS = new ArrayList<>();
            for (Movie movie : usuario.getFavoritos()) {
                MovieDTO movieDTO = null;

                try {
                    movieDTO = movieDAO.findById(movie.getId()).get();
                } catch (NoSuchElementException ex) {
                    Movie mov = new Movie(tmdbService.getMovieById(movie.getId()));
                    movieDTO = new MovieDTO(mov);
                }

                List <UsuarioDTO> currentUsers = movieDTO.getUsuarios().stream().filter(userDTO -> userDTO.getId() != id).collect(Collectors.toList());

                currentUsers.add(usuarioDTO);
                movieDTO.setUsuarios(currentUsers);

                movieDTO = movieDAO.save(movieDTO);
                movieDTOS.add(movieDTO);

            }

            usuarioDTO.setFavoritos(movieDTOS);
            log.info(usuarioDTO.toString());

            return new Usuario(usuarioDAO.save(usuarioDTO));
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario");
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la película para el usuario");
        }


    }
}
