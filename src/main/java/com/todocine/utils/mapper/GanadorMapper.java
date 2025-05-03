package com.todocine.utils.mapper;

import com.todocine.dto.GanadorDTO;
import com.todocine.entities.Ganador;
import com.todocine.entities.Premio;

import java.util.ArrayList;
import java.util.List;

public class GanadorMapper {

    public static GanadorDTO toDTO(Ganador ganador) {
        GanadorDTO ganadorDTO = new GanadorDTO();

        ganadorDTO.setPremioCod(ganador.getId().getPremio().getId());
        ganadorDTO.setPremio(ganador.getId().getPremio().getTitulo());
        ganadorDTO.setCategoria(ganador.getId().getCategoria().getNombre());
        ganadorDTO.setMovie(MovieMapper.toDTO(ganador.getId().getMovie()));
        ganadorDTO.setAnyo(ganador.getId().getAnyo());

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
