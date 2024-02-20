package com.todocine.dto;

import com.todocine.model.Categoria;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

public class CategoriaDTO {

    private String nombre;

    @DocumentReference
    private MovieDTO movie;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Categoria categoria) {
        this.nombre = categoria.getNombre();
        this.movie = new MovieDTO(categoria.getMovie().getId());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public MovieDTO getMovie() {
        return movie;
    }

    public void setMovie(MovieDTO movie) {
        this.movie = movie;
    }
}
