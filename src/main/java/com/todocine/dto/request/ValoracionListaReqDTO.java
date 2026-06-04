package com.todocine.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ValoracionListaReqDTO {

    @NotNull
    private Long listaId;

    @NotNull
    private Long usuarioId;

    private String comentario;

    private Double puntuacion;

    public ValoracionListaReqDTO() {
    }

    public ValoracionListaReqDTO(Long listaId, Long usuarioId) {
        this.listaId = listaId;
        this.usuarioId = usuarioId;
    }

    public Long getListaId() {
        return listaId;
    }

    public void setListaId(Long listaId) {
        this.listaId = listaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
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
