package com.todocine.service.impl;

import com.todocine.dao.*;
import com.todocine.dto.response.GanadorDTO;
import com.todocine.dto.response.MovieDTO;
import com.todocine.dto.request.GanadorReqDTO;
import com.todocine.entities.*;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.ConflictException;
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

    @Autowired
    private CategoriaPremioDAO categoriaPremioDAO;

    @Autowired
    private PremioDAO premioDAO;

    @Autowired
    private CategoriaDAO categoriaDAO;

    @Override
    @Transactional(readOnly = true)
    public Paginator<GanadorDTO> getGanadoresByPremioIdAnyo(Long id, Integer anyo, Integer page) {
        Paginator<GanadorDTO> paginator = new Paginator<>();
        Pageable pageable = PageRequest.of(page - 1, 21);

        Page<Ganador> ganadores = ganadorDAO.findById_CategoriaPremio_Id_Premio_IdAndId_Anyo(id, anyo, pageable);

        if (ganadores.hasContent()) {
                List<GanadorDTO> ganadoresDTOs = ganadores.getContent().stream()
                        .map(GanadorMapper::toDTO)
                        .toList();

                paginator.setPage(page);
                paginator.setResults(ganadoresDTOs);
                paginator.setTotalPages(ganadores.getTotalPages());
                paginator.setTotalResults((int)ganadores.getTotalElements());


        }

        return paginator;
    }

    @Override
    @Transactional
    public GanadorDTO insertGanador(GanadorReqDTO ganadorReqDTO) throws ConflictException, NotFoudException, BadGatewayException {
        Ganador ganador = null;
        MovieDTO movieDTO = null;
        Movie movie = null;
        GanadorId ganadorId = new GanadorId();

        CategoriaPremio categoriaPremio = new CategoriaPremio();
        CategoriaPremioId categoriaPremioId = new CategoriaPremioId(new Categoria(ganadorReqDTO.getCategoriaId()),
                new Premio(ganadorReqDTO.getPremioId()));
        categoriaPremio.setId(categoriaPremioId);

        ganadorId.setCategoriaPremio(categoriaPremio);
        ganadorId.setAnyo(ganadorReqDTO.getAnyo());
        ganadorId.setMovie(new Movie(ganadorReqDTO.getMovieId()));

        ganador = ganadorDAO.findById(ganadorId).orElse(null);

        if (ganador == null) {

            categoriaPremio = categoriaPremioDAO.findById(categoriaPremioId).orElse(null);

            if (categoriaPremio == null) {
                throw new NotFoudException(PREMIO_NOTFOUND);
            }

            movie = movieDAO.findById(ganadorReqDTO.getMovieId()).orElse(null);

            if (movie == null) {
                try {
                    Map<String, Object> map = tmdbService.getMovieById(ganadorReqDTO.getMovieId());

                    if (map.get("id") == null)
                        throw new NotFoudException(MOVIE_NOTFOUND);

                    movieDTO = MovieMapper.toDTO(map);
                    movie = MovieMapper.toEntity(movieDTO);

                    movieDAO.save(movie);
                } catch (IOException e) {
                    throw new BadGatewayException(TMDB_ERROR);
                }
            }

            Premio premio = premioDAO.findById(ganadorReqDTO.getPremioId()).orElse(null);
            Categoria categoria = categoriaDAO.findById(ganadorReqDTO.getCategoriaId()).orElse(null);
            categoriaPremioId.setPremio(premio);
            categoriaPremioId.setCategoria(categoria);

            ganadorId.setMovie(movie);

            ganador = new Ganador();
            ganador.setId(ganadorId);

            ganadorDAO.save(ganador);

            return GanadorMapper.toDTO(ganador);

        } else {
            throw new ConflictException(GANADOR_EXISTS);
        }
    }

}
