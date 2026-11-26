package com.todocine.service;

import com.todocine.dto.request.ComentarioReqDTO;
import com.todocine.dto.request.VotoComentarioReqDTO;
import com.todocine.dto.response.ComentarioDTO;
import com.todocine.dto.response.VotoComentarioDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface ComentarioService {
    List<ComentarioDTO> getComentarioReplies(Long id);

    ComentarioDTO insertarComentario(ComentarioReqDTO comentarioReqDTO);

    ComentarioDTO updateComentario(Long id, ComentarioReqDTO comentarioReqDTO);

    VotoComentarioDTO votarComentario(Long id, VotoComentarioReqDTO votoComentarioReqDTO);
}
