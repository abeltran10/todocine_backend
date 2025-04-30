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
import com.todocine.service.TMDBService;
import com.todocine.service.VotoService;
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
public class VotoServiceImpl extends BaseServiceImpl implements VotoService {
    Logger logger = LoggerFactory.getLogger(VotoServiceImpl.class);

    @Autowired
    private VotoDAO votoDAO;

    @Autowired
    private TMDBService tmdbService;

    @Autowired
    private MovieDAO movieDAO;

    @Override
    @Transactional
    public MovieDTO updateVote(String movieId,
                               VotoDTO votoDTO) throws NotFoudException, BadGatewayException {
        Movie movieEntity = null;
        MovieDTO movieDTO = null;
        logger.info(votoDTO.toString());

        if (getCurrentUserId().equals(votoDTO.getUsuarioId())) {
            try {
                Map<String, Object> map = tmdbService.getMovieById(movieId);
                if (map.get("id") != null) {
                    movieDTO = MovieMapper.toDTO(map);
                    movieEntity = movieDAO.findById(movieId).orElse(null);

                    if (movieEntity == null)
                        movieEntity = MovieMapper.toEntity(movieDTO);
                    movieDAO.save(movieEntity);

                    if (movieEntity != null) {
                        Voto voto = votoDAO.findById(new VotoId(new Usuario(getCurrentUserId()), new Movie(movieId))).orElse(null);
                        List<Voto> votosTC = movieEntity.getVotosTC();
                        if (voto != null && votosTC.contains(voto)) {
                            votosTC.remove(voto);

                            actualizarMediaVotos(movieEntity, votoDTO.getVoto(), voto.getVoto());
                            voto.setVoto(votoDTO.getVoto());
                        } else {
                            voto = VotoMapper.toEntity(votoDTO);

                            calcularMediaVotos(movieEntity, votoDTO.getVoto());
                        }

                        logger.info(voto.toString());
                        votoDAO.save(voto);
                        votosTC.add(voto);

                        movieDAO.save(movieEntity);

                        List<VotoDTO> currentVotes = movieEntity.getVotosTC().stream()
                                .map(VotoMapper::toDTO)
                                .toList();

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
        } else {
            throw new NotFoudException("El usuario no es el de la sesión");
        }
    }

    private void actualizarMediaVotos(Movie movieEntity, Double newVote, Double oldVoto) {
        Double total = movieEntity.getVotosMediaTC() * movieEntity.getTotalVotosTC();
        Double totalOld = total - oldVoto;

        movieEntity.setVotosMediaTC((totalOld + newVote) / movieEntity.getTotalVotosTC());
    }

    private void calcularMediaVotos(Movie movieEntity, Double voto) {
        Integer totalVotosTC = movieEntity.getTotalVotosTC();
        Double total = movieEntity.getVotosMediaTC() * totalVotosTC;

        ++totalVotosTC;
        movieEntity.setTotalVotosTC(totalVotosTC);
        movieEntity.setVotosMediaTC((total + voto) / totalVotosTC);
    }
}
