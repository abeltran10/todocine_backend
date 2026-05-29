package com.todocine.utils.mapper;

import com.todocine.dto.response.ValoracionDTO;
import com.todocine.entities.ValoracionLista;

import java.time.format.DateTimeFormatter;

public class ValoracionMapper {

    public static ValoracionDTO toDTO(ValoracionLista valoracionLista) {
        ValoracionDTO valoracionDTO = new ValoracionDTO(valoracionLista.getId().getLista().getId());
        valoracionDTO.setComentario(valoracionLista.getComentario());
        valoracionDTO.setPuntuacion(valoracionLista.getPuntuacion());
        valoracionDTO.setUsername(valoracionLista.getId().getUsuario().getUsername());

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String fecha = valoracionLista.getFecha().format(formato);
        valoracionDTO.setFecha(fecha);

        return valoracionDTO;
    }
}
