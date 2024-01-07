package com.todocine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoCollection {

    @JsonProperty("results")
    private List<Video> results;

    public VideoCollection() {
    }

    public VideoCollection(List<Video> results) {
        this.results = results;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "VideoPage{" +
                "results=" + results +
                '}';
    }
}
