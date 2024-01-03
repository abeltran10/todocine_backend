package com.todocine.dto;

import com.todocine.model.Genre;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Genre")
public class GenreDTO {

    @Id
    private String id;

    private String name;

    @Version
    private Integer version;

    public GenreDTO() {
    }

    @PersistenceCreator
    public GenreDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreDTO(Genre genero) {
        this.id = genero.getId();
        this.name = genero.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
