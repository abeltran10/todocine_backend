package com.todocine.dto;

import com.todocine.entities.Premio;

import java.util.List;
import java.util.stream.Collectors;

public class PremioDTO {

    private String id;

    private Integer codigo;

    private List<CategoriaDTO> categoriaDTOS;

    private String titulo;

    public PremioDTO(String id) {
        this.id = id;
    }

    public PremioDTO(String id, Integer codigo, List<CategoriaDTO> categoriaDTOS, String titulo) {
        this.id = id;
        this.codigo = codigo;
        this.categoriaDTOS = categoriaDTOS;
        this.titulo = titulo;
    }

    public PremioDTO(Premio premio) {
        this.id = premio.getId();
        this.codigo = premio.getCodigo();
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

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public List<CategoriaDTO> getCategoriaDTOS() {
        return categoriaDTOS;
    }

    public void setCategoriaDTOS(List<CategoriaDTO> categoriaDTOS) {
        this.categoriaDTOS = categoriaDTOS;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
