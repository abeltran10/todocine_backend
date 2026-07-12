package com.todocine.utils.mapper;

import com.todocine.dto.response.ListaDTO;
import com.todocine.entities.Lista;

public class ListaMapper {

    public static ListaDTO toDTO(Lista lista) {
        ListaDTO listaDTO = new ListaDTO(lista.getId());
        listaDTO.setNombre(lista.getNombre());
        listaDTO.setDescripcion(lista.getDescripcion());
        listaDTO.setUsername(lista.getUsuario().getUsername());
        listaDTO.setPublica("S".equals(lista.getPublica()));

        return listaDTO;
    }

}
