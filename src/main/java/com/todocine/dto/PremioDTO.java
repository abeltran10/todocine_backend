package com.todocine.dto;

import com.todocine.model.Premio;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "Premios")
public class PremioDTO {

    @Id
    private String id;

    private List<CategoriaDTO> categoriaDTOS;

    private String titulo;

    @Version
    private Integer version;

    public PremioDTO(String id) {
        this.id = id;
    }

    @PersistenceCreator
    public PremioDTO(String id, List<CategoriaDTO> categoriaDTOS, String titulo) {
        this.id = id;
        this.categoriaDTOS = categoriaDTOS;
        this.titulo = titulo;
    }

    public PremioDTO(Premio premio) {
        this.id = premio.getId();
        this.categoriaDTOS = premio.getCategorias().stream().map(categoria -> new CategoriaDTO(categoria))
                .collect(Collectors.toList());
        this.titulo = premio.getTitulo();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
