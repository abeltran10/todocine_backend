package com.todocine.entities;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "VALORACIONLISTA")
public class ValoracionLista {

    @EmbeddedId
    private ValoracionListaId id;

    private String comentario;

    private Double puntuacion;

    @Column(name = "FECHA", nullable = false)
    private LocalDateTime fecha;

    public ValoracionLista() {
    }

    public ValoracionLista(ValoracionListaId id) {
        this.id = id;
    }

    public ValoracionListaId getId() {
        return id;
    }

    public void setId(ValoracionListaId id) {
        this.id = id;
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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
