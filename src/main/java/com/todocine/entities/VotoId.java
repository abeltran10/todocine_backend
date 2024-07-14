package com.todocine.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Embeddable
public class VotoId {

    @ManyToOne
    @JoinColumn(name = "usuario", referencedColumnName = "ID")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "movie", referencedColumnName = "ID")
    private Movie movie;

    public VotoId() {
    }

    public VotoId(Usuario usuario, Movie movie) {
        this.usuario = usuario;
        this.movie = movie;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VotoId votoId = (VotoId) o;
        return usuario.equals(votoId.usuario) && movie.equals(votoId.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, movie);
    }

    @Override
    public String toString() {
        return "VotoId{" +
                "usuario=" + usuario.getId() +
                ", movie=" + movie.getId() +
                '}';
    }
}
