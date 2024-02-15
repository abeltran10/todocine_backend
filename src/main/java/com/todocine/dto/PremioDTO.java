package com.todocine.dto;

import com.todocine.model.Premio;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Premios")
public class PremioDTO {

    @Id
    private String id;

    @DBRef
    private CategoriaDTO categoriaDTO;

    private String titulo;

    @Version
    private Integer version;

    public PremioDTO(String id) {
        this.id = id;
    }

    @PersistenceCreator
    public PremioDTO(String id, CategoriaDTO categoriaDTO, String titulo) {
        this.id = id;
        this.categoriaDTO = categoriaDTO;
        this.titulo = titulo;
    }

    public PremioDTO(Premio premio) {
        this.id = premio.getId();
        this.categoriaDTO = new CategoriaDTO(premio.getCategoria());
        this.titulo = premio.getTitulo();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CategoriaDTO getCategoriaDTO() {
        return categoriaDTO;
    }

    public void setCategoriaDTO(CategoriaDTO categoriaDTO) {
        this.categoriaDTO = categoriaDTO;
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
