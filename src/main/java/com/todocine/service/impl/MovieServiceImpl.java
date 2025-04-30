package com.todocine.service.impl;


import com.todocine.dao.MovieDAO;
import com.todocine.dao.VotoDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.VotoDTO;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import com.todocine.entities.Voto;
import com.todocine.entities.VotoId;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.MovieService;
import com.todocine.service.TMDBService;
import com.todocine.utils.Paginator;
import com.todocine.utils.mapper.MovieMapper;
import com.todocine.utils.mapper.VotoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService {

    Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    private TMDBService tmdbService;

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private VotoDAO votoDAO;

    @Override
    @Transactional(readOnly = true)
    public MovieDTO getMovieById(String id) throws NotFoudException, BadGatewayException {
        try {
            Map<String, Object> movieMap = tmdbService.getMovieById(id);

            if (movieMap.get("id") != null) {
                MovieDTO movieDTO = MovieMapper.toDTO(movieMap);
                Movie movie = movieDAO.findById(id).orElse(null);

                if (movie != null) {
                    MovieDTO movieDTO1 = MovieMapper.toDTO(movie);
                    movieDTO.setVotos(movieDTO1.getVotos());
                    movieDTO.setVotosMediaTC(movieDTO1.getVotosMediaTC());
                    movieDTO.setTotalVotosTC(movieDTO1.getTotalVotosTC());
                }

                return movieDTO;

            } else {
                throw new NotFoudException("No se ha encontrado la película");
            }
        } catch (IOException ex) {
            throw new BadGatewayException("La respuesta de TMDB ha fallado");
        }
    }

    @Override
    public Paginator getMovieByName(String name, Integer pagina) throws NotFoudException, BadGatewayException {
        try {

            Map<String, Object> map = tmdbService.getMoviesByName(name, pagina);

            if (map.get("results") == null)
                throw new NotFoudException("No se ha encontrado la película con ese nombre");
            else {
                Paginator<MovieDTO> moviePage = new Paginator<>(map);
                List<MovieDTO> results = ((List<Map<String, Object>>) map.get("results")).stream()
                        .map(MovieMapper::toDTO)
                        .toList();

                List<MovieDTO> page = results.stream()
                        .limit(21)
                        .skip((pagina - 1) * 21 )
                        .toList();

                moviePage.setPage(pagina);
                moviePage.setResults(page);
                moviePage.setTotalPages((results.size() / (21 + 1)) + 1);
                moviePage.setTotalResults(results.size());


                logger.info(moviePage.toString());

                return moviePage;
            }

        } catch (IOException e) {
            throw new BadGatewayException("La respuesta de TMDB ha fallado");
        }

    }

    @Override
    public Paginator getMoviesPlayingNow(String country, Integer pagina) throws NotFoudException, BadGatewayException {
        try {

            Map<String, Object> map = tmdbService.getMoviesPlayingNow(country, pagina);

            if (map.get("results") == null)
                throw new NotFoudException("No se ha encontrado la cartelera para esa región");
            else {
                Paginator<MovieDTO> moviePage = new Paginator<>(map);
                List<MovieDTO> results = ((List<Map<String, Object>>) map.get("results")).stream()
                        .map(MovieMapper::toDTO)
                        .toList();

                List<MovieDTO> page = results.stream()
                        .limit(21)
                        .skip((pagina - 1) * 21 )
                        .toList();

                moviePage.setPage(pagina);
                moviePage.setResults(page);
                moviePage.setTotalPages((results.size() / (21 + 1)) + 1);
                moviePage.setTotalResults(results.size());

                logger.info(moviePage.toString());

                return moviePage;
            }
        } catch (IOException e) {
            throw new BadGatewayException("La respuesta de TMDB ha fallado");
        }
    }

}

