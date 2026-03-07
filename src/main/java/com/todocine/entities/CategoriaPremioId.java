package com.todocine.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Embeddable
public class CategoriaPremioId {

    @ManyToOne
    @JoinColumn(name = "categoria", referencedColumnName = "ID")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "premio", referencedColumnName = "ID")
    private Premio premio;

    public CategoriaPremioId() {
    }

    public CategoriaPremioId(Categoria categoria, Premio premio) {
        this.categoria = categoria;
        this.premio = premio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Premio getPremio() {
        return premio;
    }

    public void setPremio(Premio premio) {
        this.premio = premio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaPremioId that = (CategoriaPremioId) o;
        return Objects.equals(categoria, that.categoria) && Objects.equals(premio, that.premio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoria, premio);
    }
}
