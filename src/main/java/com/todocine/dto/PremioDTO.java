package com.todocine.dto;

import com.todocine.model.Premio;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Premios")
public class PremioDTO {

    @Id
    private String id;

    private String categoría;

    private String titulo;

    @Version
    private Integer version;

    public PremioDTO(String id) {
        this.id = id;
    }

    @PersistenceCreator
    public PremioDTO(String id, String categoría, String titulo) {
        this.id = id;
        this.categoría = categoría;
        this.titulo = titulo;
    }

    public PremioDTO(Premio premio) {
        this.id = premio.getId();
        this.categoría = premio.getCategoria();
        this.titulo = premio.getTitulo();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoría() {
        return categoría;
    }

    public void setCategoría(String categoría) {
        this.categoría = categoría;
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
