package com.todocine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.todocine.entities.Categoria;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriaDTO {

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("movie")
    private MovieDTO movie;

    public CategoriaDTO(String nombre, MovieDTO movie) {
        this.nombre = nombre;
        this.movie = movie;
    }

    public CategoriaDTO(Categoria categoria) {
        this.nombre = categoria.getNombre();
        this.movie = new MovieDTO(categoria.getMovie());
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

    public void setMovie(MovieDTO movieDTO) {
        this.movie = movieDTO;
    }
}
