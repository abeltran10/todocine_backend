package com.todocine.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "PREMIO")
public class Premio {

    @Id
    private Long id;

    private String titulo;

    public Premio() {
    }

    public Premio(Long id) {
        this.id = id;
    }


    public Premio(Long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Premio premio)) return false;
        return Objects.equals(id, premio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
