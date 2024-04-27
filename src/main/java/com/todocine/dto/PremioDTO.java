package com.todocine.dto;

import com.todocine.entities.Premio;

import java.util.List;
import java.util.stream.Collectors;

public class PremioDTO {

    private String id;

    private List<CategoriaDTO> categoriaDTOS;

    private String titulo;

    public PremioDTO(String id) {
        this.id = id;
    }

    public PremioDTO(String id, List<CategoriaDTO> categoriaDTOS, String titulo) {
        this.id = id;
        this.categoriaDTOS = categoriaDTOS;
        this.titulo = titulo;
    }

    public PremioDTO(Premio premio) {
        this.id = premio.getId();
        this.categoriaDTOS = premio.getCategorias().stream().map(categoriaDTO -> new CategoriaDTO(categoriaDTO))
                .collect(Collectors.toList());
        this.titulo = premio.getTitulo();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CategoriaDTO> getCategorias() {
        return categoriaDTOS;
    }

    public void setCategorias(List<CategoriaDTO> categoriaDTOS) {
        this.categoriaDTOS = categoriaDTOS;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
