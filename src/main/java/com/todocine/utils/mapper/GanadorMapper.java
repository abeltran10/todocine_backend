package com.todocine.utils.mapper;

import com.todocine.dto.GanadorDTO;
import com.todocine.dto.GanadorDetailDTO;
import com.todocine.entities.Ganador;

import java.util.ArrayList;
import java.util.List;

public class GanadorMapper {

    public static GanadorDTO toDTO(Ganador ganador) {
        GanadorDTO ganadorDTO = new GanadorDTO();

        ganadorDTO.setPremioId(ganador.getId().getPremio().getId());
        ganadorDTO.setCategoriaId(ganador.getId().getCategoria().getId());
        ganadorDTO.setAnyo(ganador.getId().getAnyo());
        ganadorDTO.setMovieId(ganador.getId().getMovie().getId());

        return ganadorDTO;
    }
}
