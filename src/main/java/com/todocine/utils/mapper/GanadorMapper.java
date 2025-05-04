package com.todocine.utils.mapper;

import com.todocine.dto.GanadorDTO;
import com.todocine.entities.Ganador;

import java.util.ArrayList;
import java.util.List;

public class GanadorMapper {

    public static GanadorDTO toDTO(Ganador ganador) {
        GanadorDTO ganadorDTO = new GanadorDTO();

        ganadorDTO.setPremioId(ganador.getId().getPremio().getId());
        ganadorDTO.setPremio(ganador.getId().getPremio().getTitulo());
        ganadorDTO.setCategoria(ganador.getId().getCategoria().getNombre());
        ganadorDTO.setAnyo(ganador.getId().getAnyo());
        ganadorDTO.setMovieId(ganador.getId().getMovie().getId());
        ganadorDTO.setOriginalLanguage(ganador.getId().getMovie().getOriginalLanguage());
        ganadorDTO.setOriginalTitle(ganador.getId().getMovie().getOriginalTitle());
        ganadorDTO.setTitle(ganador.getId().getMovie().getTitle());
        ganadorDTO.setGenreDTOS(new ArrayList<>());
        ganadorDTO.setVideoDTOS(new ArrayList<>());
        ganadorDTO.setOverview(ganador.getId().getMovie().getOverview());
        ganadorDTO.setPopularity(ganador.getId().getMovie().getPopularity());
        ganadorDTO.setPosterPath(ganador.getId().getMovie().getPosterPath());
        ganadorDTO.setReleaseDate(ganador.getId().getMovie().getReleaseDate());
        ganadorDTO.setTotalVotosTC(ganador.getId().getMovie().getTotalVotosTC());
        ganadorDTO.setVoteAverage(ganador.getId().getMovie().getVoteAverage());
        ganadorDTO.setVoteCount(ganador.getId().getMovie().getVoteCount());
        ganadorDTO.setTotalVotosTC(ganador.getId().getMovie().getTotalVotosTC());

        return ganadorDTO;
    }

    public static List<GanadorDTO> toDTOList(List<Ganador> ganadorList) {
        List<GanadorDTO> ganadorDTOList = new ArrayList<>();

        ganadorDTOList.addAll(ganadorList.stream()
                .map(GanadorMapper::toDTO)
                .toList());

        return ganadorDTOList;
    }
}
