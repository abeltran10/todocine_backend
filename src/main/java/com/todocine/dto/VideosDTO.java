package com.todocine.dto;

import com.todocine.model.Video;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class VideosDTO {

    @Id
    private String id;

    private String name;

    private String key;

    private String site;

    private String type;

    @Version
    private Integer version;

    public VideosDTO() {
    }

    @PersistenceCreator
    public VideosDTO(String id, String name, String key, String site, String type, Integer version) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.site = site;
        this.type = type;
        this.version = version;
    }

    public VideosDTO(Video video) {
        this.id = video.getId();
        this.name = video.getName();
        this.key = video.getKey();
        this.site = video.getSite();
        this.type = video.getType();
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
