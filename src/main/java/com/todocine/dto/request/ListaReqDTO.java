package com.todocine.dto.request;

import com.todocine.dto.response.MovieDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListaReqDTO {

    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String descripcion;

    @NotNull
    private Long usuarioId;

    private Boolean publica;

    public ListaReqDTO() {
    }

    public ListaReqDTO(Long id) {
        this.id = id;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Boolean getPublica() {
        return publica;
    }

    public void setPublica(Boolean publica) {
        this.publica = publica;
    }
}
