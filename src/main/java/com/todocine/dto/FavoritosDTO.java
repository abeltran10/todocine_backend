package com.todocine.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FavoritosDTO {

    @JsonIgnore
    private Long usuarioId;

    @JsonProperty("movieId")
    @NotBlank
    private String movieId;

    public FavoritosDTO() {
    }

    public FavoritosDTO(Long usuarioId, String movieId) {
        this.usuarioId = usuarioId;
        this.movieId = movieId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
