package com.todocine.service.impl;


import com.todocine.dao.MovieDAO;
import com.todocine.dao.VotoDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.VotoDTO;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.model.Movie;
import com.todocine.model.Voto;
import com.todocine.service.MovieService;
import com.todocine.service.TMDBService;
import com.todocine.utils.Paginator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
    public Movie getMovieById(String id) throws NotFoudException, BadGatewayException {
        try {
            Map<String, Object> movieMap = tmdbService.getMovieById(id);

            if (movieMap.get("id") == null) {
                throw new NotFoudException("No se ha encontrado la película");
            } else {
                Movie movie = new Movie(movieMap);
                return movie;
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
                Paginator<Movie> moviePage = new Paginator<>(map);
                List<Movie> results = ((List<Map<String, Object>>) map.get("results")).stream()
                        .map(item -> new Movie(item)).collect(Collectors.toList());
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
                Paginator<Movie> moviePage = new Paginator<>(map);
                List<Movie> results = ((List<Map<String, Object>>) map.get("results")).stream()
                        .map(item -> new Movie(item)).collect(Collectors.toList());
                moviePage.setResults(results);

                logger.info(moviePage.toString());

                return moviePage;
            }
        } catch (IOException e) {
            throw new BadGatewayException("La respuesta de TMDB ha fallado");
        }
    }

    @Override
    public Movie addVote(String id, Voto voto) throws BadGatewayException {
        MovieDTO dto = null;
        Movie movie = null;

        try {
            Map<String, Object> map = tmdbService.getMovieById(id);
            if (map.get("id") != null) {
                movie = new Movie(map);
            }

            dto = movieDAO.findById(id).orElse(null);

            if (dto == null)
                dto = new MovieDTO(movie);

            VotoDTO votoDTO = new VotoDTO(voto);
            votoDAO.save(votoDTO);

            dto.getVotosTC().add(votoDTO);

            Integer totalVotosTC = dto.getTotalVotosTC();
            Double total = dto.getVotosMediaTC() * totalVotosTC;

            ++totalVotosTC;
            dto.setTotalVotosTC(totalVotosTC);
            dto.setVotosMediaTC((total + voto.getVoto()) / totalVotosTC);

            movieDAO.save(dto);

            List<Voto> currentVotes = dto.getVotosTC().stream().map(votoDTO1 -> new Voto(votoDTO1)).collect(Collectors.toList());
            movie.setVotos(currentVotes);
            movie.setTotalVotosTC(dto.getTotalVotosTC());
            movie.setVotosMediaTC(dto.getVotosMediaTC());

            return movie;
        } catch (IOException e) {
            throw new BadGatewayException("La respuesta de TMDB ha fallado");
        }

    }

    @Override
    public Movie updateVote(String movieId, String votoId, Voto voto) throws NotFoudException {
        MovieDTO dto = null;
        Movie movie = null;

        try {
            Map<String, Object> map = tmdbService.getMovieById(movieId);
            if (map.get("id") != null) {
                movie = new Movie(map);
            }

            try {
                dto = movieDAO.findById(movieId).get();

                VotoDTO votoDTO = votoDAO.findById(votoId).get();
                List<VotoDTO> votosTC = dto.getVotosTC();
                if (votosTC.contains(votoDTO)) {
                    votosTC.remove(votoDTO);

                    Double total = dto.getVotosMediaTC() * dto.getTotalVotosTC();
                    Double totalOld = total - votoDTO.getVoto();

                    dto.setVotosMediaTC((totalOld + voto.getVoto()) / dto.getTotalVotosTC());
                    votoDTO.setVoto(voto.getVoto());

                    votoDAO.save(votoDTO);

                    votosTC.add(votoDTO);
                    movieDAO.save(dto);

                    List<Voto> currentVotes = dto.getVotosTC().stream().map(votoDTO1 -> new Voto(votoDTO1)).collect(Collectors.toList());
                    movie.setVotos(currentVotes);
                    movie.setTotalVotosTC(dto.getTotalVotosTC());
                    movie.setVotosMediaTC(dto.getVotosMediaTC());

                    return movie;
                } else {
                    throw new NotFoudException("No hay un voto anterior");
                }
            } catch (NoSuchElementException ex) {
                throw new NotFoudException("No hay un voto anterior");
            }
        } catch (IOException ex) {
            throw new NotFoudException("No se ha encontrado la película");
        }
    }

}
