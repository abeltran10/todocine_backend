package com.todocine.utils.mapper;

import com.todocine.dto.response.ListaDTO;
import com.todocine.dto.response.MovieDTO;
import com.todocine.entities.Lista;

import java.util.List;

public class ListaMapper {

    public static ListaDTO toDTO(Lista lista) {
        ListaDTO listaDTO = new ListaDTO(lista.getId());
        listaDTO.setNombre(lista.getNombre());
        listaDTO.setDescripcion(lista.getDescripcion());

        List<MovieDTO> movieDTOList = lista.getMovies().stream()
                        .map(MovieMapper::toDTO).toList();
        listaDTO.setMovies(movieDTOList);

        listaDTO.setUsername(lista.getUsuario().getUsername());
        listaDTO.setPublica("S".equals(lista.getPublica()));

        return listaDTO;
    }

}
