package com.todocine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.todocine.dto.VideosDTO;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Video {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("key")
    private String key;

    @JsonProperty("site")
    private String site;

    @JsonProperty("type")
    private String type;

    public Video() {
    }

    public Video(String id, String name, String key, String site, String type) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.site = site;
        this.type = type;
    }

    public Video(VideosDTO videosDTO) {
        this.id = videosDTO.getId();
        this.name = videosDTO.getName();
        this.key = videosDTO.getKey();
        this.site = videosDTO.getSite();
        this.type = videosDTO.getType();
    }

    public Video(Map<String, Object> map) {
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
