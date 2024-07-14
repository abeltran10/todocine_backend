package com.todocine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenreDTO {

    @JsonProperty("id")
    @NotBlank
    private String id;

    @JsonProperty("name")
    private String name;

    public GenreDTO() {
    }

    public GenreDTO(String id) {
        this.id = id;
    }

    public GenreDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreDTO(Map<String, Object> map) {
        this.id = String.valueOf(map.get("id"));
        this.name = (map.containsKey("name")) ? (String) map.get("name") : null;
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
