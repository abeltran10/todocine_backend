package com.todocine.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ValoracionListaDTO {

    @NotNull
    private Long listaId;

    @NotBlank
    private String username;

    private String comentario;

    private Double puntuacion;

    public ValoracionListaDTO() {
    }

    public ValoracionListaDTO(Long listaId, String username) {
        this.listaId = listaId;
        this.username = username;
    }

    public Long getListaId() {
        return listaId;
    }

    public void setListaId(Long listaId) {
        this.listaId = listaId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Double puntuacion) {
        this.puntuacion = puntuacion;
    }
}
