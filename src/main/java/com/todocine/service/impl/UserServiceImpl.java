package com.todocine.service.impl;

import com.todocine.dao.FavoritosDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.entities.Favoritos;
import com.todocine.entities.FavoritosId;
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
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private FavoritosDAO favoritosDAO;

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
    public UsuarioDTO getUsuarioById(Long id) throws NotFoudException {
        Usuario usuario = usuarioDAO.findById(id).get();

        if (usuario == null)
            throw new NotFoudException("No existe el usuario con ese nombre");
        else {
            return new UsuarioDTO(usuario);
        }
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO) throws NotFoudException {
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
    @Transactional(readOnly = true)
    public Paginator<MovieDTO> getUsuarioFavs(Long id, Integer page) throws NotFoudException {
        Paginator<MovieDTO> paginator = new Paginator<>();
        List<Favoritos> favoritos = favoritosDAO.findByIdUsuarioId(id);

        if (favoritos != null && !favoritos.isEmpty()) {
            List<MovieDTO> movieDTOList = favoritos.stream().map(favs-> new MovieDTO(favs.getId().getMovie())).collect(Collectors.toList());

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
    @Transactional
    public MovieDTO addFavoritosByUserId(Long id, MovieDTO movieDTO) throws BadRequestException, NotFoudException {
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
                    movieDAO.save(movie);
                }
            }

            Favoritos favorito = new Favoritos(new FavoritosId(usuario, movie));
            if (!usuario.getFavoritos().contains(favorito)) {
                usuario.getFavoritos().add(favorito);
                favoritosDAO.save(favorito);

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
    @Transactional
    public void deleteFavoritosByUserId(Long id, String movieId) throws BadRequestException, NotFoudException {
        Movie movie = null;

        try {
            Usuario usuario = usuarioDAO.findById(id).get();

            try {
                movie = movieDAO.findById(movieId).get();

                List<Favoritos> currentFavs = usuario.getFavoritos();

                log.info("currentFavs: " + currentFavs);

                Favoritos favorito = new Favoritos(new FavoritosId(usuario, movie));
                if (currentFavs.contains(favorito)) {
                    currentFavs.remove(favorito);
                    log.info("favs user: " + usuario.getFavoritos());

                    favoritosDAO.delete(favorito);
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
