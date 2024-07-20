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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        MovieDTO movieDTO1 = null;
        try {
            Map<String, Object> movieMap = tmdbService.getMovieById(id);

            if (movieMap.get("id") != null) {
                MovieDTO movieDTO = new MovieDTO(movieMap);
                Movie movie = movieDAO.findById(id).orElse(null);

                if (movie != null) {
                    movieDTO1 = new MovieDTO(movie);
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
                        .map(item -> new MovieDTO(item)).collect(Collectors.toList());
                moviePage.setResults(results);

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
                        .map(item -> new MovieDTO(item)).collect(Collectors.toList());
                moviePage.setResults(results);

                logger.info(moviePage.toString());

                return moviePage;
            }
        } catch (IOException e) {
            throw new BadGatewayException("La respuesta de TMDB ha fallado");
        }
    }

    @Override
    @Transactional
    public MovieDTO updateVote(String movieId, Long usuarioId, VotoDTO votoDTO) throws NotFoudException, BadGatewayException {
        Movie movieEntity = null;
        MovieDTO movieDTO = null;
        logger.info(votoDTO.toString());
        try {
            Map<String, Object> map = tmdbService.getMovieById(movieId);
            if (map.get("id") != null) {
                movieDTO = new MovieDTO(map);
                movieEntity = movieDAO.findById(movieId).orElse(null);

                if (movieEntity == null)
                    movieEntity = new Movie(movieDTO);
                    movieDAO.save(movieEntity);

                if (movieEntity != null) {
                    Voto voto = votoDAO.findById(new VotoId(new Usuario(usuarioId), new Movie(movieId))).orElse(null);
                    List<Voto> votosTC = movieEntity.getVotosTC();
                    if (voto != null && votosTC.contains(voto)) {
                        votosTC.remove(voto);

                        Double total = movieEntity.getVotosMediaTC() * movieEntity.getTotalVotosTC();
                        Double totalOld = total - voto.getVoto();

                        movieEntity.setVotosMediaTC((totalOld + votoDTO.getVoto()) / movieEntity.getTotalVotosTC());
                        voto.setVoto(votoDTO.getVoto());

                    } else {
                        voto = new Voto(votoDTO);

                        Integer totalVotosTC = movieEntity.getTotalVotosTC();
                        Double total = movieEntity.getVotosMediaTC() * totalVotosTC;

                        ++totalVotosTC;
                        movieEntity.setTotalVotosTC(totalVotosTC);
                        movieEntity.setVotosMediaTC((total + votoDTO.getVoto()) / totalVotosTC);
                    }

                    logger.info(voto.toString());
                    votoDAO.save(voto);
                    votosTC.add(voto);

                    movieDAO.save(movieEntity);

                    List<VotoDTO> currentVotes = movieEntity.getVotosTC().stream().map(v -> new VotoDTO(v)).collect(Collectors.toList());
                    movieDTO.setVotos(currentVotes);
                    movieDTO.setTotalVotosTC(movieEntity.getTotalVotosTC());
                    movieDTO.setVotosMediaTC(movieEntity.getVotosMediaTC());
                }

                return movieDTO;
            } else {
                throw new NotFoudException("No se ha encontrado la película");
            }
        } catch (IOException ex) {
            throw new BadGatewayException("La respuesta de TMDB ha fallado");
        }
    }
}

