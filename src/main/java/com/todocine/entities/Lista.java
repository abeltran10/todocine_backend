package com.todocine.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Lista")
public class Lista {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_entity_generator")
    @SequenceGenerator(name = "sequence_entity_generator", allocationSize = 1, sequenceName = "sequence_entity_generator")
    private Long id;

    private String nombre;

    private String descripcion;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "LISTAMOVIES",
            joinColumns = @JoinColumn(name = "lista", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "movie", referencedColumnName = "ID")
    )
    private List<Movie> movies = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "usuario", referencedColumnName = "ID")
    private Usuario usuario;

    public Lista() {
    }

    public Lista(Long id) {
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

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Lista lista)) return false;
        return Objects.equals(id, lista.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
