package com.todocine.utils.mapper;

import com.todocine.dto.response.ValoracionListaDTO;
import com.todocine.entities.ValoracionLista;

import java.time.format.DateTimeFormatter;

public class ValoracionMapper {

    public static ValoracionListaDTO toDTO(ValoracionLista valoracionLista) {
        ValoracionListaDTO valoracionListaDTO = new ValoracionListaDTO(valoracionLista.getId().getLista().getId());
        valoracionListaDTO.setComentario(valoracionLista.getComentario());
        valoracionListaDTO.setPuntuacion(valoracionLista.getPuntuacion());
        valoracionListaDTO.setUsername(valoracionLista.getId().getUsuario().getUsername());

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String fecha = valoracionLista.getFecha().format(formato);
        valoracionListaDTO.setFecha(fecha);

        return valoracionListaDTO;
    }
}
