package com.todocine.entities;

import com.todocine.dto.CategoriaDTO;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

public class Categoria {

    private String nombre;

    @DocumentReference
    private Movie movie;

    public Categoria() {
    }

    public Categoria(CategoriaDTO categoriaDTO) {
        this.nombre = categoriaDTO.getNombre();
        this.movie = new Movie(categoriaDTO.getMovie());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
