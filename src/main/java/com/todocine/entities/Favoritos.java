package com.todocine.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "FAVORITOS")
public class Favoritos {

    @EmbeddedId
    private FavoritosId id;

    public Favoritos() {
    }

    public Favoritos(FavoritosId id) {
        this.id = id;
    }

    public FavoritosId getId() {
        return id;
    }

    public void setId(FavoritosId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favoritos favoritos = (Favoritos) o;
        return id.equals(favoritos.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
