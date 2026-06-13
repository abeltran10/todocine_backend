package com.todocine.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ValoracionListaDTO {

    @NotNull
    private Long listaId;

    private String comentario;

    @NotNull
    private Double puntuacion;

    @NotBlank
    private String fecha;

    @NotBlank
    private String username;

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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
