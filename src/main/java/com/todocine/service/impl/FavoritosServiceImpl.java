package com.todocine.service.impl;

import com.todocine.dao.FavoritosDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.entities.Favoritos;
import com.todocine.entities.FavoritosId;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.FavoritosService;
import com.todocine.service.TMDBService;
import com.todocine.utils.Paginator;
import com.todocine.utils.mapper.MovieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class FavoritosServiceImpl extends BaseServiceImpl implements FavoritosService {
    Logger log = LoggerFactory.getLogger(FavoritosServiceImpl.class);

    @Autowired
    private TMDBService tmdbService;

    @Autowired
    private FavoritosDAO favoritosDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private MovieDAO movieDAO;

    @Override
    @Transactional(readOnly = true)
    public Paginator<MovieDTO> getUsuarioFavs(Long userId, Integer page) throws NotFoudException {
        Paginator<MovieDTO> paginator = new Paginator<>();
        Pageable pageable = PageRequest.of(page - 1, 21);

        if (getCurrentUserId().equals(userId)) {
            Page<Favoritos> favoritos = favoritosDAO.findByIdUsuarioId(getCurrentUserId(), pageable);

            if (favoritos.hasContent()) {
                List<MovieDTO> movieDTOList = favoritos.getContent().stream()
                        .map(favs-> MovieMapper.toDTO(favs.getId().getMovie()))
                        .collect(Collectors.toList());

                paginator.setPage(page);
                paginator.setResults(movieDTOList);
                paginator.setTotalPages(favoritos.getTotalPages());
                paginator.setTotalResults((int)favoritos.getTotalElements());

                return paginator;

            } else {
                throw new NotFoudException("No hay favoritos para el usuario");
            }
        } else {
            throw new NotFoudException("El usuario no es el de la sesión");
        }





    }

    @Override
    @Transactional
    public MovieDTO addFavoritos(Long userId, MovieDTO movieDTO) throws BadRequestException, NotFoudException {
        Movie movie = null;

        if (getCurrentUserId().equals(userId)) {
            try {
                Usuario usuario = usuarioDAO.findById(getCurrentUserId()).get();

                try {
                    movie = movieDAO.findById(movieDTO.getId()).get();
                } catch (NoSuchElementException ex) {
                    Map<String, Object> movieMap = tmdbService.getMovieById(movieDTO.getId());

                    if (movieMap.get("id") == null)
                        throw new NotFoudException("No existe la película");
                    else {
                        MovieDTO peli = MovieMapper.toDTO(movieMap);
                        movie = MovieMapper.toEntity(peli);
                        movieDAO.save(movie);
                    }
                }

                Favoritos favorito = new Favoritos(new FavoritosId(usuario, movie));
                if (!usuario.getFavoritos().contains(favorito)) {
                    usuario.getFavoritos().add(favorito);
                    favoritosDAO.save(favorito);

                    return MovieMapper.toDTO(movie);

                } else
                    throw new BadRequestException("La película ya está en favoritos");

            } catch (NoSuchElementException ex) {
                throw new NotFoudException("No existe el usuario");
            } catch (IOException ex) {
                throw new NotFoudException("No existe la película");
            }
        } else {
            throw new NotFoudException("El usuario no es el de la sesión");
        }


    }

    @Override
    @Transactional
    public void deleteFavoritos(Long userId, String movieId) throws BadRequestException, NotFoudException {
        Movie movie = null;

        if (getCurrentUserId().equals(userId)) {
            try {
                Usuario usuario = usuarioDAO.findById(getCurrentUserId()).get();

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
        } else {
            throw new NotFoudException("El usuario no es el de la sesión");
        }

    }

}
