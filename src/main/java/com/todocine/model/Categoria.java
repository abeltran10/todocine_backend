package com.todocine.model;

import com.todocine.dto.CategoriaDTO;

public class Categoria {

    private String nombre;

    private Movie movie;

    public Categoria(String nombre, Movie movie) {
        this.nombre = nombre;
        this.movie = movie;
    }

    public Categoria(CategoriaDTO categoriaDTO) {
        this.nombre = categoriaDTO.getNombre();
        this.movie = new Movie(categoriaDTO.getMovie().getId(), categoriaDTO.getMovie().getOriginalTitle(),
                categoriaDTO.getMovie().getPosterPath());
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
