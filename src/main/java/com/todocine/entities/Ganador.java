package com.todocine.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ganador ganador)) return false;
        return Objects.equals(id, ganador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
