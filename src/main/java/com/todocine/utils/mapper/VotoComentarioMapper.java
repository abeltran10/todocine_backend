package com.todocine.utils.mapper;

import com.todocine.dto.response.VotoComentarioDTO;
import com.todocine.entities.VotoComentario;
import com.todocine.enums.TipoVoto;

public class VotoComentarioMapper {

    public static VotoComentarioDTO toDTO(VotoComentario votoComentario) {
        VotoComentarioDTO votoComentarioDTO = new VotoComentarioDTO();

        votoComentarioDTO.setUsername(votoComentario.getId().getUsuario().getUsername());
        votoComentarioDTO.setComentarioId(votoComentario.getId().getComentario().getId());
        votoComentarioDTO.setVoto(votoComentario.getTipo().getValue());

        return votoComentarioDTO;
    }
}
