package com.todocine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.todocine.dto.GenreDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Genre {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    public Genre() {
    }

    public Genre(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(GenreDTO genreDTO) {
        this.id = genreDTO.getId();
        this.name = genreDTO.getName();
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

    @Override
    public String toString() {
        return "Genre{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
