package com.todocine.dto.response;

import com.todocine.entities.Comentario;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import com.todocine.entities.VotoComentario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String username;

    @NotNull
    private Long movieId;

    @NotBlank
    private String contenido;

    @NotBlank
    private String fecha;

    @NotNull
    private Boolean hasReplies;

    private List<VotoComentarioDTO> votes = new ArrayList<>();

    public ComentarioDTO() {
    }

    public ComentarioDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Boolean getHasReplies() {
        return hasReplies;
    }

    public void setHasReplies(Boolean hasReplies) {
        this.hasReplies = hasReplies;
    }

    public Boolean hasReplies() {
        return this.hasReplies;
    }

    public List<VotoComentarioDTO> getVotes() {
        return votes;
    }

    public void setVotes(List<VotoComentarioDTO> votes) {
        this.votes = votes;
    }
}
