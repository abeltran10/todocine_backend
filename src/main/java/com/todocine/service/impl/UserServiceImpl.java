package com.todocine.service.impl;

import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.model.Movie;
import com.todocine.model.Usuario;
import com.todocine.service.TMDBService;
import com.todocine.service.UsuarioService;
import com.todocine.utils.Paginator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


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
    public Usuario insertUsuario(Usuario usuario) throws ResponseStatusException {
        UsuarioDTO usuarioDTO = usuarioDAO.findByUsername(usuario.getUsername());

        if (usuarioDTO == null) {
            usuarioDTO = new UsuarioDTO();
            usuarioDTO.setUsername(usuario.getUsername());
            usuarioDTO.setPassword(passwordEncoder().encode(usuario.getPassword()));
            usuarioDTO.setEnabled(true);
            usuarioDTO.setCredentialsNonExpired(true);
            usuarioDTO.setAccountNonLocked(true);
            usuarioDTO.setAccountNonExpired(true);
            usuarioDTO.setFavoritos(new ArrayList<>());

            usuarioDAO.save(usuarioDTO);

            return new Usuario(usuarioDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un usuario con ese nombre ya existe");
        }
    }

    @Override
    public Usuario updateUsuario(String id, Usuario usuario) throws ResponseStatusException {
       log.info("updateUsuario");
        UsuarioDTO usuarioDTO = null;
        try {
            usuarioDTO = usuarioDAO.findById(id).get();
            usuarioDTO.setPassword(passwordEncoder().encode(usuario.getPassword()));
            usuarioDTO.setEnabled(usuario.getEnabled());
            usuarioDTO.setAccountNonExpired(usuario.getAccountNonExpired());
            usuarioDTO.setAccountNonLocked(usuario.getAccountNonLocked());
            usuarioDTO.setCredentialsNonExpired(usuario.getCredentialsNonExpired());

            usuarioDAO.save(usuarioDTO);

            return new Usuario(usuarioDTO);
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario");
        }

    }

    @Override
    public Paginator<Movie> getUsuarioFavs(String id, Integer page) throws ResponseStatusException {
        Paginator<Movie> paginator = new Paginator<>();
        List<MovieDTO> movieDTOS = movieDAO.findByUserId(id);

        if (movieDTOS != null && !movieDTOS.isEmpty()) {
            List<Movie> movieList = movieDTOS.stream().map(movieDTO -> new Movie(movieDTO)).collect(Collectors.toList());

            int totalResults = movieList.size();
            int totalPages = totalResults / (20 + 1) + 1;

            List<Movie> results = movieList.stream()
                    .skip((page - 1) * 20)
                    .limit(20).collect(Collectors.toList());

            paginator.setPage(page);
            paginator.setResults(results);
            paginator.setTotalPages(totalPages);
            paginator.setTotalResults(totalResults);

            return paginator;

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay favoritos para el usuario");
        }


    }

    @Override
    public Usuario addFavoritosByUserId(String id, Movie movie) throws ResponseStatusException {
        MovieDTO  movieDTO = null;

        try {
            UsuarioDTO usuarioDTO = usuarioDAO.findById(id).get();

            try {
                movieDTO = movieDAO.findById(movie.getId()).get();
            } catch (NoSuchElementException ex) {
                Map<String, Object> movieMap = tmdbService.getMovieById(movie.getId());

                if (movieMap.get("id") == null)
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la película");
                else {
                    Movie peli = new Movie(movieMap);
                    movieDTO = new MovieDTO(peli);
                }
            }

            if (!movieDTO.getUsuarios().contains(usuarioDTO) && !usuarioDTO.getFavoritos().contains(movieDTO)) {
                movieDTO.getUsuarios().add(usuarioDTO);
                usuarioDTO.getFavoritos().add(movieDAO.save(movieDTO));

                return new Usuario(usuarioDAO.save(usuarioDTO));

            } else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La película ya está en favoritos");

        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario");
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la película");
        }
    }

    @Override
    public void deleteFavoritosByUserId(String id, String movieId) throws ResponseStatusException {
        MovieDTO movieDTO = null;

        try {
            UsuarioDTO usuarioDTO = usuarioDAO.findById(id).get();

            try {
                movieDTO = movieDAO.findById(movieId).get();

                List<UsuarioDTO> currentUsers = movieDTO.getUsuarios().stream()
                        .filter(userDTO -> !userDTO.getId().equals(id))
                        .collect(Collectors.toList());

                movieDTO.setUsuarios(currentUsers);
                movieDAO.save(movieDTO);

                List<MovieDTO> currentFavs = usuarioDTO.getFavoritos().stream()
                        .filter(movDTO -> !movDTO.getId().equals(movieId)).collect(Collectors.toList());

                usuarioDTO.setFavoritos(currentFavs);
                usuarioDAO.save(usuarioDTO);
            } catch (NoSuchElementException ex) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la película");
            }

        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario");
        }

    }
}
