package com.todocine.service.impl;


import com.todocine.configuration.Constants;
import com.todocine.dao.UsuarioMovieDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.MovieDetailDTO;
import com.todocine.entities.*;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.MovieService;
import com.todocine.service.TMDBService;
import com.todocine.utils.Paginator;
import com.todocine.utils.mapper.MovieMapper;
import com.todocine.utils.mapper.UsuarioMovieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.todocine.configuration.Constants.*;

@Service
public class MovieServiceImpl extends BaseServiceImpl implements MovieService {

    Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    private TMDBService tmdbService;

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private UsuarioMovieDAO usuarioMovieDAO;

    @Override
    @Transactional(readOnly = true)
    public MovieDetailDTO getMovieDetailById(String id) throws NotFoudException, BadGatewayException {
        Boolean favorito = false;
        Boolean vista = false;
        Double voto = null;

        try {
            Map<String, Object> movieMap = tmdbService.getMovieById(id);

            if (movieMap.get("id") != null) {
                MovieDTO movieDTO = MovieMapper.toDTO(movieMap);
                Movie movie = movieDAO.findById(id).orElse(null);

                if (movie != null) {
                    MovieDTO movieDTO1 = MovieMapper.toDTO(movie);
                    movieDTO.setVotosMediaTC(movieDTO1.getVotosMediaTC());
                    movieDTO.setTotalVotosTC(movieDTO1.getTotalVotosTC());
                }

                UserMovieId userMovieId = new UserMovieId(new Usuario(getCurrentUserId()), MovieMapper.toEntity(movieDTO));

                UsuarioMovie usuarioMovie = usuarioMovieDAO.findById(userMovieId).orElse(null);

                if (usuarioMovie != null) {
                    favorito = usuarioMovie.getFavoritos().equals("S");
                    vista = usuarioMovie.getVista().equals("S");
                    voto = usuarioMovie.getVoto();
                }

                return new MovieDetailDTO(movieDTO, favorito, voto, vista);

            } else {
                throw new NotFoudException(MOVIE_NOTFOUND);
            }
        } catch (IOException ex) {
            throw new BadGatewayException(TMDB_ERROR);
        }
    }

    @Override
    public Paginator getMovies(Map<String, String> filters, Integer pagina) throws NotFoudException, BadRequestException, BadGatewayException {
        Map<String, Object> map = new HashMap<>();

        try {

            if (!filters.get(MOVIE_NAME).isBlank()) {
                map = tmdbService.getMoviesByName(filters.get(MOVIE_NAME), pagina);
            } else if (!filters.get(MOVIE_STATUS).isBlank() && !filters.get(MOVIE_REGION).isBlank()) {
                map = tmdbService.getMoviesPlayingNow(filters.get(MOVIE_REGION), pagina);
            } else {
                throw new BadRequestException(MOVIE_SEARCH_BADREQUEST);
            }

            if (map.get("results") == null)
                throw new NotFoudException(MOVIE_NOTFOUND);
            else {
                Paginator<MovieDTO> moviePage = new Paginator<>(map);
                List<MovieDTO> results = ((List<Map<String, Object>>) map.get("results")).stream()
                        .map(MovieMapper::toDTO)
                        .toList();

                moviePage.setResults(results);

                logger.info(moviePage.toString());

                return moviePage;
            }

        } catch (IOException e) {
            throw new BadGatewayException(TMDB_ERROR);
        }
    }

}

