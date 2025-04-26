package com.todocine.entities;

import com.todocine.dto.PremioDTO;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "PREMIO")
public class Premio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_entity_generator")
    @SequenceGenerator(name = "sequence_entity_generator", allocationSize = 1, sequenceName = "sequence_entity_generator")
    private Long id;

    private Integer codigo;

    private String titulo;

    public Premio() {
    }

    public Premio(Long id) {
        this.id = id;
    }


    public Premio(Long id, Integer codigo, String titulo) {
        this.id = id;
        this.codigo = codigo;
        this.titulo = titulo;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
