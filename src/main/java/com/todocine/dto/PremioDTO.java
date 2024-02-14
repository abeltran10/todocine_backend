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

    private String categoria;

    private String titulo;

    private MovieDTO movie;

    @Version
    private Integer version;

    public PremioDTO(String id) {
        this.id = id;
    }

    @PersistenceCreator
    public PremioDTO(String id, String categoria, String titulo, MovieDTO movie) {
        this.id = id;
        this.categoria = categoria;
        this.titulo = titulo;
        this.movie = movie;
    }

    public PremioDTO(Premio premio) {
        this.id = premio.getId();
        this.categoria = premio.getCategoria();
        this.titulo = premio.getTitulo();
        this.movie = new MovieDTO(premio.getMovie());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public MovieDTO getMovie() {
        return movie;
    }

    public void setMovie(MovieDTO movie) {
        this.movie = movie;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
