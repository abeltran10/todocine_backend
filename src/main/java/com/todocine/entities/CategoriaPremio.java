package com.todocine.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "CATEGORIAPREMIO")
public class CategoriaPremio {

    @EmbeddedId
    private CategoriaPremioId id;

    public CategoriaPremio() {
    }

    public CategoriaPremio(CategoriaPremioId id) {
        this.id = id;
    }

    public CategoriaPremioId getId() {
        return id;
    }

    public void setId(CategoriaPremioId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaPremio that = (CategoriaPremio) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
