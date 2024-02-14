package com.todocine.model;

import com.todocine.dto.PremioDTO;

public class Premio {

    private String id;

    private String categoria;

    private String titulo;

    private Movie movie;

    public Premio(String id) {
        this.id = id;
    }

    public Premio(String id, String categoria, String titulo, Movie movie) {
        this.id = id;
        this.categoria = categoria;
        this.titulo = titulo;
        this.movie = movie;
    }

    public Premio(PremioDTO premioDTO) {
        this.id = premioDTO.getId();
        this.categoria = premioDTO.getCategoria();
        this.titulo = premioDTO.getTitulo();
        this.movie = new Movie(premioDTO.getMovie());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
