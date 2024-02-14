package com.todocine.model;

import com.todocine.dto.PremioDTO;

public class Premio {

    private String id;

    private String categoria;

    private String titulo;

    public Premio(String id) {
        this.id = id;
    }

    public Premio(String id, String categoria, String titulo) {
        this.id = id;
        this.categoria = categoria;
        this.titulo = titulo;
    }

    public Premio(PremioDTO premioDTO) {
        this.id = premioDTO.getId();
        this.categoria = premioDTO.getCategoría();
        this.titulo = premioDTO.getTitulo();
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
}
