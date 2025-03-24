package com.todocine.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.todocine.entities.Categoria;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriaDTO {

    @JsonIgnore
    private Long id;


    @JsonProperty("nombre")
    private String nombre;

    public CategoriaDTO(Long id, Long premioId, String nombre) {
        this.id = id;
        this.nombre = nombre;

    }

    public CategoriaDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nombre = categoria.getNombre();

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
