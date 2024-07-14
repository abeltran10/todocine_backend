package com.todocine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.todocine.entities.Premio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PremioDTO {

    @JsonProperty("id")
    @NotBlank
    private Long id;

    @JsonProperty("codigo")
    @NotNull
    private Integer codigo;

    @JsonProperty("categorias")
    private List<CategoriaDTO> categorias;

    @JsonProperty("titulo")
    private String titulo;

    public PremioDTO(Long id) {
        this.id = id;
    }

    public PremioDTO(Long id, Integer codigo, List<CategoriaDTO> categorias, String titulo) {
        this.id = id;
        this.codigo = codigo;
        this.categorias = categorias;
        this.titulo = titulo;
    }

    public PremioDTO(Premio premio) {
        this.id = premio.getId();
        this.codigo = premio.getCodigo();
        this.categorias = premio.getCategorias().stream().map(categoriaDTO -> new CategoriaDTO(categoriaDTO))
                .collect(Collectors.toList());
        this.titulo = premio.getTitulo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public List<CategoriaDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaDTO> categorias) {
        this.categorias = categorias;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
