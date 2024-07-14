package com.todocine.entities;

import com.todocine.dto.PremioDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

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

    @OneToMany(mappedBy = "premio", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Categoria> categorias;

    private String titulo;

    public Premio() {
    }

    public Premio(Long id) {
        this.id = id;
    }


    public Premio(Long id, Integer codigo, List<Categoria> categorias, String titulo) {
        this.id = id;
        this.codigo = codigo;
        this.categorias = categorias;
        this.titulo = titulo;
    }

    public Premio(PremioDTO premioDTO) {
        this.id = premioDTO.getId();
        this.codigo = premioDTO.getCodigo();
        this.categorias = premioDTO.getCategorias().stream().map(categoria -> new Categoria(categoria))
                .collect(Collectors.toList());
        this.titulo = premioDTO.getTitulo();
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
