package com.todocine.dto.request;

import com.todocine.dto.response.MovieDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListaReqDTO {

    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String descripcion;

    private List<MovieDTO> movies = new ArrayList<>();

    @NotBlank
    private String username;

    @NotNull
    private Boolean publica;

    public ListaReqDTO() {
    }

    public ListaReqDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<MovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDTO> movies) {
        this.movies = movies;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getPublica() {
        return publica;
    }

    public void setPublica(Boolean publica) {
        this.publica = publica;
    }
}
