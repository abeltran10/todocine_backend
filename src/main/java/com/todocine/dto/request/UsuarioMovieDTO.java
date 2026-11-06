package com.todocine.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioMovieDTO {

    @JsonProperty("username")
    @NotNull
    private String username;

    @JsonProperty("movieId")
    @NotNull
    private Long movieId;

    @JsonProperty("favoritos")
    @NotNull
    private Boolean favoritos;

    @JsonProperty("vista")
    @NotNull
    private Boolean vista;

    @JsonProperty("voto")
    private Double voto;

    public UsuarioMovieDTO() {
    }

    public UsuarioMovieDTO(String username, Long movieId) {
        this.username = username;
        this.movieId = movieId;
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

    public Boolean getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(Boolean favoritos) {
        this.favoritos = favoritos;
    }

    public Boolean getVista() {
        return vista;
    }

    public void setVista(Boolean vista) {
        this.vista = vista;
    }

    public Double getVoto() {
        return voto;
    }

    public void setVoto(Double voto) {
        this.voto = voto;
    }
}
