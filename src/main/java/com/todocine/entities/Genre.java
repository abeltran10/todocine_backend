package com.todocine.entities;

import com.todocine.dto.GenreDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Genre")
public class Genre {

    @Id
    private String id;

    private String name;

    @Version
    private Integer version;

    public Genre() {
    }

    @PersistenceCreator
    public Genre(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(String id) {
        this.id = id;
    }

    public Genre(GenreDTO genero) {
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
