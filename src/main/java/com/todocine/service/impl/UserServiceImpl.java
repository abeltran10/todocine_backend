package com.todocine.service.impl;

import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.dto.MovieDTO;
import com.todocine.service.TMDBService;
import com.todocine.service.UsuarioService;
import com.todocine.utils.Paginator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);

        Usuario usuario = usuarioDAO.findByUsername(username);

        if (usuario == null)
            throw new UsernameNotFoundException("Usuario o contraseña incorrectos");
        else
            return usuario;

    }



    @Override
    public UsuarioDTO getUsuarioById(String id) throws NotFoudException {
        Usuario usuario = usuarioDAO.findById(id).get();

        if (usuario == null)
            throw new NotFoudException("No existe el usuario con ese nombre");
        else {
            return new UsuarioDTO(usuario);
        }
    }

    @Override
    public UsuarioDTO getUsuarioByName(String username) throws NotFoudException {
        log.info("getUsuarioByName");

        Usuario usuario = usuarioDAO.findByUsername(username);

        if (usuario == null)
            throw new NotFoudException("No existe el usuario");
        else {
            return new UsuarioDTO(usuario);
        }



    }

    @Override
    public UsuarioDTO insertUsuario(UsuarioDTO usuarioDTO) throws BadRequestException {
        Usuario usuario = usuarioDAO.findByUsername(usuarioDTO.getUsername());

        if (usuario == null) {
            usuario = new Usuario();
            usuario.setUsername(usuarioDTO.getUsername());
            usuario.setPassword(passwordEncoder().encode(usuarioDTO.getPassword()));
            usuario.setEnabled(true);
            usuario.setCredentialsNonExpired(true);
            usuario.setAccountNonLocked(true);
            usuario.setAccountNonExpired(true);
            usuario.setFavoritos(new ArrayList<>());

            usuarioDAO.save(usuario);

            return new UsuarioDTO(usuario);
        } else {
            throw new BadRequestException("Un usuario con ese nombre ya existe");
        }
    }

    @Override
    public UsuarioDTO updateUsuario(String id, UsuarioDTO usuarioDTO) throws NotFoudException {
       log.info("updateUsuario");
        Usuario usuario = null;
        try {
            usuario = usuarioDAO.findById(id).get();
            usuario.setPassword(passwordEncoder().encode(usuarioDTO.getPassword()));
            usuario.setEnabled(usuarioDTO.getEnabled());
            usuario.setAccountNonExpired(usuarioDTO.getAccountNonExpired());
            usuario.setAccountNonLocked(usuarioDTO.getAccountNonLocked());
            usuario.setCredentialsNonExpired(usuarioDTO.getCredentialsNonExpired());

            usuarioDAO.save(usuario);

            return new UsuarioDTO(usuario);
        } catch (NoSuchElementException ex) {
            throw new NotFoudException("No existe el usuario");
        }

    }

    @Override
    public Paginator<MovieDTO> getUsuarioFavs(String id, Integer page) throws NotFoudException {
        Paginator<MovieDTO> paginator = new Paginator<>();
        List<Movie> movieEntities = usuarioDAO.findFavoritosById(id);

        if (movieEntities != null && !movieEntities.isEmpty()) {
            List<MovieDTO> movieDTOList = movieEntities.stream().map(movieDTO -> new MovieDTO(movieDTO)).collect(Collectors.toList());

            int totalResults = movieDTOList.size();
            int totalPages = totalResults / (20 + 1) + 1;

            List<MovieDTO> results = movieDTOList.stream()
                    .skip((page - 1) * 20)
                    .limit(20).collect(Collectors.toList());

            paginator.setPage(page);
            paginator.setResults(results);
            paginator.setTotalPages(totalPages);
            paginator.setTotalResults(totalResults);

            return paginator;

        } else {
            throw new NotFoudException("No hay favoritos para el usuario");
        }


    }

    @Override
    public MovieDTO addFavoritosByUserId(String id, MovieDTO movieDTO) throws BadRequestException, NotFoudException {
        Movie movie = null;

        try {
            Usuario usuario = usuarioDAO.findById(id).get();

            try {
                movie = movieDAO.findById(movieDTO.getId()).get();
            } catch (NoSuchElementException ex) {
                Map<String, Object> movieMap = tmdbService.getMovieById(movieDTO.getId());

                if (movieMap.get("id") == null)
                    throw new NotFoudException("No existe la película");
                else {
                    MovieDTO peli = new MovieDTO(movieMap);
                    movie = new Movie(peli);
                }
            }

            if (!usuario.getFavoritos().contains(movie)) {
                usuario.getFavoritos().add(movie);
                usuarioDAO.save(usuario);

                return new MovieDTO(movie);

            } else
                throw new BadRequestException("La película ya está en favoritos");

        } catch (NoSuchElementException ex) {
            throw new NotFoudException("No existe el usuario");
        } catch (IOException ex) {
            throw new NotFoudException("No existe la película");
        }
    }

    @Override
    public void deleteFavoritosByUserId(String id, String movieId) throws BadRequestException, NotFoudException {
        Movie movie = null;

        try {
            Usuario usuario = usuarioDAO.findById(id).get();

            try {
                movie = movieDAO.findById(movieId).get();

                List<Movie> currentFavs = usuario.getFavoritos();
                log.info("currentFavs: " + currentFavs);
                if (currentFavs.contains(movie)) {
                    currentFavs.remove(movie);
                    log.info("favs user: " + usuario.getFavoritos());

                    usuarioDAO.save(usuario);
                } else {
                    throw new BadRequestException("La película no está en favoritos");
                }

            } catch (NoSuchElementException ex) {
                throw new NotFoudException("No existe la película");
            }

        } catch (NoSuchElementException ex) {
            throw new NotFoudException("No existe el usuario");
        }

    }
}
