package com.todocine.entities;

import com.todocine.dto.PremioDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "Premios")
public class Premio {

    @Id
    private String id;

    private Integer codigo;

    private List<Categoria> categorias;

    private String titulo;

    @Version
    private Integer version;

    public Premio(String id) {
        this.id = id;
    }

    @PersistenceCreator
    public Premio(String id, Integer codigo, List<Categoria> categorias, String titulo) {
        this.id = id;
        this.codigo = codigo;
        this.categorias = categorias;
        this.titulo = titulo;
    }

    public Premio(PremioDTO premioDTO) {
        this.id = premioDTO.getId();
        this.codigo = premioDTO.getCodigo();
        this.categorias = premioDTO.getCategoriaDTOS().stream().map(categoria -> new Categoria(categoria))
                .collect(Collectors.toList());
        this.titulo = premioDTO.getTitulo();
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
