package com.todocine.dto.response;

import com.todocine.entities.Comentario;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import com.todocine.entities.VotoComentario;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDTO {

    private Long id;

    private String username;

    private Long movieId;

    private String contenido;

    private String fecha;

    private List<VotoComentarioDTO> votes = new ArrayList<>();

}
