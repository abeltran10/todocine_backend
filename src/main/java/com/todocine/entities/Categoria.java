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

    @Column(name = "nombre")
    private String nombre;

    public Categoria() {
    }

    public Categoria(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;

    }

    public Categoria(CategoriaDTO categoriaDTO) {
        this.nombre = categoriaDTO.getNombre();

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

}
