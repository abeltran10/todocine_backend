package com.todocine.entities;

import com.todocine.dto.CategoriaDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "CATEGORIA")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_entity_generator")
    @SequenceGenerator(name = "sequence_entity_generator", allocationSize = 1, sequenceName = "sequence_entity_generator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "premio", referencedColumnName = "id")
    private Premio premio;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "movie", referencedColumnName = "id")
    private Movie movie;

    public Categoria() {
    }

    public Categoria(Long id, Premio premio, String nombre, Movie movie) {
        this.id = id;
        this.premio = premio;
        this.nombre = nombre;
        this.movie = movie;
    }

    public Categoria(CategoriaDTO categoriaDTO) {
        this.nombre = categoriaDTO.getNombre();
        this.movie = new Movie(categoriaDTO.getMovie());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Premio getPremio() {
        return premio;
    }

    public void setPremio(Premio premio) {
        this.premio = premio;
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
