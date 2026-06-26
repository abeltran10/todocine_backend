package com.todocine.utils.mapper;

import com.todocine.dto.response.GanadorDTO;
import com.todocine.entities.Ganador;

import java.time.format.DateTimeFormatter;

public class GanadorMapper {

    public static GanadorDTO toDTO(Ganador ganador) {
        GanadorDTO ganadorDTO = new GanadorDTO();

        ganadorDTO.setPremioId(ganador.getId().getCategoriaPremio().getId().getPremio().getId());
        ganadorDTO.setPremio(ganador.getId().getCategoriaPremio().getId().getPremio().getTitulo());
        ganadorDTO.setCategoriaId(ganador.getId().getCategoriaPremio().getId().getCategoria().getId());
        ganadorDTO.setCategoria(ganador.getId().getCategoriaPremio().getId().getCategoria().getNombre());
        ganadorDTO.setAnyo(ganador.getId().getAnyo());
        ganadorDTO.setMovieId(ganador.getId().getMovie().getId());
        ganadorDTO.setOriginalTitle(ganador.getId().getMovie().getOriginalTitle());
        ganadorDTO.setTitle(ganador.getId().getMovie().getTitle());
        ganadorDTO.setOverview(ganador.getId().getMovie().getOverview());
        ganadorDTO.setPosterPath(ganador.getId().getMovie().getPosterPath());

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fecha = ganador.getId().getMovie().getReleaseDate().format(formato);
        ganadorDTO.setReleaseDate(fecha);

        return ganadorDTO;
    }
}
