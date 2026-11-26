package com.todocine.utils.mapper;

import com.todocine.dto.request.ComentarioReqDTO;
import com.todocine.dto.response.ComentarioDTO;
import com.todocine.dto.response.VotoComentarioDTO;
import com.todocine.entities.Comentario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ComentarioMapper {

    public static ComentarioDTO toDTO(Comentario comentario) {
        ComentarioDTO comentarioDTO = new ComentarioDTO();

        comentarioDTO.setId(comentario.getId());
        comentarioDTO.setUsername(comentario.getUsuario().getUsername());
        comentarioDTO.setMovieId(comentario.getMovie().getId());

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fecha = comentario.getFechaCreacion().format(formato);
        comentarioDTO.setFecha(fecha);

        comentarioDTO.setHasReplies(!comentario.getReplies().isEmpty());

        List<VotoComentarioDTO> votoComentarios = comentario.getVotes().stream().map(VotoComentarioMapper::toDTO).toList();
        comentarioDTO.setVotes(votoComentarios);

        return comentarioDTO;
    }

 }
