package com.todocine.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MovieListaDTO {

    @NotNull
    private Long id;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("title")
    @NotBlank
    private String title;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("poster_path")
    @NotBlank
    private String posterPath;

    @JsonProperty("release_date")
    @NotBlank
    private String releaseDate;

    public MovieListaDTO() {
    }

    public MovieListaDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
