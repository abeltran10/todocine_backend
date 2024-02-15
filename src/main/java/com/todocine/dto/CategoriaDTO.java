package com.todocine.dto;

import com.todocine.model.Categoria;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

public class CategoriaDTO {

    private String nombre;

    @DocumentReference
    private MovieDTO movieDTO;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Categoria categoria) {
        this.nombre = categoria.getNombre();
        this.movieDTO = new MovieDTO(categoria.getMovie());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public MovieDTO getMovieDTO() {
        return movieDTO;
    }

    public void setMovieDTO(MovieDTO movieDTO) {
        this.movieDTO = movieDTO;
    }
}
