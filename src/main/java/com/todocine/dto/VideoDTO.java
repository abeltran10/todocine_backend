package com.todocine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.todocine.entities.Video;
import jakarta.validation.constraints.NotBlank;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoDTO {

    @JsonProperty("id")
    @NotBlank
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("key")
    private String key;

    @JsonProperty("site")
    private String site;

    @JsonProperty("type")
    private String type;

    public VideoDTO() {
    }

    public VideoDTO(String id) {
        this.id = id;
    }

    public VideoDTO(String id, String name, String key, String site, String type) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.site = site;
        this.type = type;
    }

    public VideoDTO(Video video) {
        this.id = video.getId();
        this.name = video.getName();
        this.key = video.getKey();
        this.site = video.getSite();
        this.type = video.getType();
    }

    public VideoDTO(Map<String, Object> map) {
        this.id = (String) map.get("id");
        this.name = (String) map.get("name");
        this.key = (String) map.get("key");
        this.site = (String) map.get("site");
        this.type = (String) map.get("type");
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", site='" + site + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
