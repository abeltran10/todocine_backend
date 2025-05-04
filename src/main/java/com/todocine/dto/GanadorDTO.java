package com.todocine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class GanadorDTO {

    @JsonProperty("premioId")
    private Long premioId;

    @JsonProperty("premio")
    private String premio;

    @JsonProperty("categoria")
    private String categoria;

    @JsonProperty("anyo")
    private Integer anyo;

    @JsonProperty("movieId")
    @NotBlank
    private String movieId;

    @JsonProperty("original_title")
    @NotBlank
    private String originalTitle;

    @JsonProperty("title")
    @NotBlank
    private String title;

    @JsonProperty("poster_path")
    @NotBlank
    private String posterPath;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("release_date")
    @NotBlank
    private String releaseDate;

    @JsonProperty("popularity")
    @NotNull
    private Double popularity;

    @JsonProperty("vote_count")
    @NotNull
    private Integer voteCount;

    @JsonProperty("vote_average")
    @NotNull
    private Double voteAverage;

    @JsonProperty("genres")
    @NotNull
    private List<GenreDTO> genreDTOS;

    @JsonProperty("original_language")
    @NotBlank
    private String originalLanguage;

    @JsonProperty("videos")
    private List<VideoDTO> videoDTOS;

    @JsonProperty("total_votos_TC")
    @NotNull
    private Integer totalVotosTC;

    @JsonProperty("votos_media_TC")
    @NotNull
    private Double votosMediaTC;


    public GanadorDTO() {
    }

    public Long getPremioId() {
        return premioId;
    }

    public void setPremioId(Long premioId) {
        this.premioId = premioId;
    }

    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getAnyo() {
        return anyo;
    }

    public void setAnyo(Integer anyo) {
        this.anyo = anyo;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
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

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public List<GenreDTO> getGenreDTOS() {
        return genreDTOS;
    }

    public void setGenreDTOS(List<GenreDTO> genreDTOS) {
        this.genreDTOS = genreDTOS;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public List<VideoDTO> getVideoDTOS() {
        return videoDTOS;
    }

    public void setVideoDTOS(List<VideoDTO> videoDTOS) {
        this.videoDTOS = videoDTOS;
    }

    public Integer getTotalVotosTC() {
        return totalVotosTC;
    }

    public void setTotalVotosTC(Integer totalVotosTC) {
        this.totalVotosTC = totalVotosTC;
    }

    public Double getVotosMediaTC() {
        return votosMediaTC;
    }

    public void setVotosMediaTC(Double votosMediaTC) {
        this.votosMediaTC = votosMediaTC;
    }
}
