package com.todocine.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "GANADOR")
public class Ganador {

    @EmbeddedId
    private GanadorId id;

    public Ganador() {
    }

    public GanadorId getId() {
        return id;
    }

    public void setId(GanadorId id) {
        this.id = id;
    }

}
