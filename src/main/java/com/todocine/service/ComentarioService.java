package com.todocine.service;

import com.todocine.dto.VotoComentarioDTO;
import com.todocine.dto.request.ComentarioReqDTO;
import com.todocine.dto.response.ComentarioDTO;

import java.util.List;

public interface ComentarioService {
    List<ComentarioDTO> getComentariosByMovie(Long id);

    List<ComentarioDTO> getComentarioReplies(Long id);

    ComentarioDTO guardarComentario(Long movieId, ComentarioReqDTO comentarioReqDTO);

    ComentarioDTO votarComentario(Long id, VotoComentarioDTO votoComentarioDTO);
}
