package com.todocine.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Embeddable
public class GanadorId {

    @ManyToOne
    @JoinColumn(name = "premio", referencedColumnName = "ID")
    private Premio premio;

    @ManyToOne
    @JoinColumn(name = "categoria", referencedColumnName = "ID")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "movie", referencedColumnName = "ID")
    private Movie movie;

    @Column(name = "ANYO")
    private Integer anyo;

    public GanadorId() {
    }

    public GanadorId(Premio premio, Categoria categoria, Movie movie, Integer anyo) {
        this.premio = premio;
        this.categoria = categoria;
        this.movie = movie;
        this.anyo = anyo;
    }

    public Premio getPremio() {
        return premio;
    }

    public void setPremio(Premio premio) {
        this.premio = premio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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
        return Objects.equals(premio, ganadorId.premio) && Objects.equals(categoria, ganadorId.categoria)
                && Objects.equals(movie, ganadorId.movie) && Objects.equals(anyo, ganadorId.anyo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(premio, categoria, movie, anyo);
    }
}
