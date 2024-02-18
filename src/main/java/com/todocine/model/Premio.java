package com.todocine.model;

import com.todocine.dto.PremioDTO;

import java.util.List;
import java.util.stream.Collectors;

public class Premio {

    private String id;

    private List<Categoria> categorias;

    private String titulo;

    public Premio(String id) {
        this.id = id;
    }

    public Premio(String id, List<Categoria> categorias, String titulo) {
        this.id = id;
        this.categorias = categorias;
        this.titulo = titulo;
    }

    public Premio(PremioDTO premioDTO) {
        this.id = premioDTO.getId();
        this.categorias = premioDTO.getCategorias().stream().map(categoriaDTO -> new Categoria(categoriaDTO))
                .collect(Collectors.toList());
        this.titulo = premioDTO.getTitulo();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
