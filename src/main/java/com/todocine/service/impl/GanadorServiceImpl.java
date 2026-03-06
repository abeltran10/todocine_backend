package com.todocine.service.impl;

import com.todocine.dao.GanadorDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dto.GanadorDetailDTO;
import com.todocine.dto.GanadorDTO;
import com.todocine.dto.MovieDTO;
import com.todocine.entities.*;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.GanadorService;
import com.todocine.service.TMDBService;
import com.todocine.utils.Paginator;
import com.todocine.utils.mapper.GanadorMapper;
import com.todocine.utils.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

import static com.todocine.configuration.Constants.*;

@Service
public class GanadorServiceImpl implements GanadorService {

    @Autowired
    private GanadorDAO ganadorDAO;

    @Autowired
    private TMDBService tmdbService;

    @Autowired
    private MovieDAO movieDAO;

    @Override
    @Transactional(readOnly = true)
    public Paginator<GanadorDetailDTO> getGanadoresByPremioIdAnyo(Long id, Integer anyo, Integer page) throws NotFoudException {
        Paginator<GanadorDetailDTO> paginator = new Paginator<>();
        Pageable pageable = PageRequest.of(page - 1, 21);

        Page<Ganador> ganadores = ganadorDAO.findByIdPremioIdAndIdAnyo(id, anyo, pageable);

        if (ganadores.hasContent()) {
                List<GanadorDetailDTO> ganadoresDTOs = ganadores.getContent().stream()
                        .map(GanadorDetailDTO::new)
                        .toList();

                paginator.setPage(page);
                paginator.setResults(ganadoresDTOs);
                paginator.setTotalPages(ganadores.getTotalPages());
                paginator.setTotalResults((int)ganadores.getTotalElements());

                return paginator;
        }

        throw new NotFoudException(GANADORES_NOTFOUND);

    }

    @Override
    @Transactional
    public GanadorDTO insertGanador(GanadorDTO ganadorDTO) throws BadRequestException, NotFoudException, BadGatewayException {
        Ganador ganador = null;
        MovieDTO movieDTO = null;
        Movie movie = null;

        GanadorId ganadorId = new GanadorId();
        ganadorId.setPremio(new Premio(ganadorDTO.getPremioId()));
        ganadorId.setCategoria(new Categoria(ganadorDTO.getCategoriaId()));
        ganadorId.setAnyo(ganadorDTO.getAnyo());
        ganadorId.setMovie(new Movie(ganadorDTO.getMovieId()));

        ganador = ganadorDAO.findById(ganadorId).orElse(null);

        if (ganador == null) {
            movie = movieDAO.findById(ganadorDTO.getMovieId()).orElse(null);

            if (movie == null) {
                try {
                    Map<String, Object> map = tmdbService.getMovieById(ganadorDTO.getMovieId());

                    if (map.get("id") == null)
                        throw new NotFoudException(MOVIE_NOTFOUND);

                    movieDTO = MovieMapper.toDTO(map);
                    movie = MovieMapper.toEntity(movieDTO);

                    movieDAO.save(movie);
                } catch (IOException e) {
                    throw new BadGatewayException(TMDB_ERROR);
                }
            }

            ganador = new Ganador();
            ganador.setId(ganadorId);

            ganadorDAO.save(ganador);

            return GanadorMapper.toDTO(ganador);

        } else {
            throw new BadRequestException(GANADOR_EXISTS);
        }
    }

}
