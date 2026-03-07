package com.todocine.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Embeddable
public class GanadorId {

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "categoria", referencedColumnName = "CATEGORIA"),
            @JoinColumn(name = "premio", referencedColumnName = "PREMIO")
    })
    private CategoriaPremio categoriaPremio;

    @ManyToOne
    @JoinColumn(name = "movie", referencedColumnName = "ID")
    private Movie movie;

    @Column(name = "ANYO")
    private Integer anyo;

    public GanadorId() {
    }

    public GanadorId(CategoriaPremio categoriaPremio, Movie movie, Integer anyo) {
        this.categoriaPremio = categoriaPremio;
        this.movie = movie;
        this.anyo = anyo;
    }

    public CategoriaPremio getCategoriaPremio() {
        return categoriaPremio;
    }

    public void setCategoriaPremio(CategoriaPremio categoriaPremio) {
        this.categoriaPremio = categoriaPremio;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Integer getAnyo() {
        return anyo;
    }

    public void setAnyo(Integer anyo) {
        this.anyo = anyo;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GanadorId ganadorId)) return false;
        return Objects.equals(categoriaPremio, ganadorId.categoriaPremio)
                && Objects.equals(movie, ganadorId.movie) && Objects.equals(anyo, ganadorId.anyo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoriaPremio, movie, anyo);
    }
}
