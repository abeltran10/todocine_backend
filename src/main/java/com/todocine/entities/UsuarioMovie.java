package com.todocine.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "USUARIOMOVIE")
public class UsuarioMovie {

    @EmbeddedId
    private UserMovieId id;

    private String favoritos;

    private String vista;

    private Double voto;

    public UsuarioMovie() {
    }

    public UsuarioMovie(UserMovieId id) {
        this.id = id;
    }

    public UserMovieId getId() {
        return id;
    }

    public void setId(UserMovieId id) {
        this.id = id;
    }

    public String getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(String favoritos) {
        this.favoritos = favoritos;
    }

    public String getVista() {
        return vista;
    }

    public void setVista(String vista) {
        this.vista = vista;
    }

    public Double getVoto() {
        return voto;
    }

    public void setVoto(Double voto) {
        this.voto = voto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioMovie userMovie = (UsuarioMovie) o;
        return id.equals(userMovie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
