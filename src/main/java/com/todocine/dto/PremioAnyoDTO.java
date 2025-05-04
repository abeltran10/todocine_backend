package com.todocine.dto;

import java.util.ArrayList;
import java.util.List;

public class PremioAnyoDTO {

    private Long id;

    private String titulo;

    private List<Integer> anyos;

    public PremioAnyoDTO() {
        this.anyos = new ArrayList<>();
    }

    public PremioAnyoDTO(Long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
        this.anyos = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Integer> getAnyos() {
        return anyos;
    }

    public void setAnyos(List<Integer> anyos) {
        this.anyos = anyos;
    }

}
