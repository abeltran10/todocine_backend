package com.todocine.dto;

import com.todocine.entities.Categoria;

public class CategoriaDTO {

    private String nombre;

    private MovieDTO movieDTO;

    public CategoriaDTO(String nombre, MovieDTO movieDTO) {
        this.nombre = nombre;
        this.movieDTO = movieDTO;
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

    public MovieDTO getMovie() {
        return movieDTO;
    }

    public void setMovie(MovieDTO movieDTO) {
        this.movieDTO = movieDTO;
    }
}
