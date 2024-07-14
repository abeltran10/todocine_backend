package com.todocine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FavoritosDTO {

    @JsonProperty("usuario")
    private Long usuarioId;

    @JsonProperty("movie")
    private MovieDTO movie;

    public FavoritosDTO() {
    }

    public FavoritosDTO(Long usuarioId, MovieDTO movie) {
        this.usuarioId = usuarioId;
        this.movie = movie;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public MovieDTO getMovie() {
        return movie;
    }

    public void setMovie(MovieDTO movie) {
        this.movie = movie;
    }
}
