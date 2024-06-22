package com.todocine.entities;

import com.todocine.dto.VideoDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Video")
public class Video {

    @Id
    private String id;

    private String name;

    private String key;

    private String site;

    private String type;

    @Version
    private Integer version;

    public Video() {
    }

    @PersistenceCreator
    public Video(String id, String name, String key, String site, String type, Integer version) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.site = site;
        this.type = type;
        this.version = version;
    }

    public Video(VideoDTO videoDTO) {
        this.id = videoDTO.getId();
        this.name = videoDTO.getName();
        this.key = videoDTO.getKey();
        this.site = videoDTO.getSite();
        this.type = videoDTO.getType();
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
