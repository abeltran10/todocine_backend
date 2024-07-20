package com.todocine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.todocine.entities.Voto;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VotoDTO {

    @JsonProperty("usuarioId")
    @NotBlank
    private Long usuarioId;

    @NotBlank
    private String movieId;

    @JsonProperty("voto")
    @NotBlank
    private Double voto;

    public VotoDTO() {
    }

    public VotoDTO(Long usuarioId, String movieId) {
        this.usuarioId = usuarioId;
        this.movieId = movieId;
    }

    public VotoDTO(Long usuarioId, String movieId, Double voto) {
        this.usuarioId = usuarioId;
        this.movieId = movieId;
        this.voto = voto;
    }

    public VotoDTO(Voto voto) {
        this.usuarioId = voto.getId().getUsuario().getId();
        this.movieId = voto.getId().getMovie().getId();
        this.voto = voto.getVoto();
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

    public Double getVoto() {
        return voto;
    }

    public void setVoto(Double voto) {
        this.voto = voto;
    }

    @Override
    public String toString() {
        return "VotoDTO{" +
                "usuarioId=" + usuarioId +
                ", movieId='" + movieId + '\'' +
                ", voto=" + voto +
                '}';
    }
}
