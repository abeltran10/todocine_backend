package com.todocine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.todocine.dto.MovieDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {


    @JsonProperty("id")
    private String id;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("title")
    private String title;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("popularity")
    private Double popularity;

    @JsonProperty("vote_count")
    private Integer voteCount;

    @JsonProperty("vote_average")
    private Double voteAverage;

    @JsonProperty("genres")
    private List<Genre> genres;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("videos")
    private List<Video> videos;

    public Movie() {
    }


    public Movie(String id, String originalTitle, String title, String posterPath, String overview, String releaseDate, Double popularity,
                 Integer voteCount, Double voteAverage, List<Genre> genres, String originalLanguage, List<Video> videos) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.genres = genres;
        this.originalLanguage = originalLanguage;
        this.videos = videos;
    }

    public Movie(MovieDTO movieDTO) {
        this.id = movieDTO.getId();
        this.originalTitle = movieDTO.getOriginalTitle();
        this.title = movieDTO.getTitle();
        this.posterPath = movieDTO.getPosterPath();
        this.overview = movieDTO.getOverview();
        this.releaseDate = movieDTO.getReleaseDate();
        this.popularity = movieDTO.getPopularity();
        this.voteCount = movieDTO.getVoteCount();
        this.voteAverage = movieDTO.getVoteAverage();
        this.genres = movieDTO.getGenreIds().stream().map(genreDTO ->  new Genre(genreDTO) ).collect(Collectors.toList());
        this.originalLanguage = movieDTO.getOriginalLanguage();
    }

    public Movie(Map<String, Object> map) {
        this.id = String.valueOf(map.get("id"));
        this.originalTitle = (String) map.get("original_title");
        this.title = (String) map.get("title");
        this.posterPath = (String) map.get("poster_path");
        this.overview = (String) map.get("overview");
        this.releaseDate = (String) map.get("release_date");
        this.popularity = (Double) map.get("popularity");
        this.voteCount = (Integer) map.get("vote_count");
        this.voteAverage = (Double) map.get("vote_average");
        this.genres = ((List<Map<String, Object>>) map.get("genres")).stream()
                .map(item -> new Genre(item)).collect(Collectors.toList());
        this.originalLanguage = (String) map.get("original_language");
        this.videos = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", popularity=" + popularity +
                ", voteCount=" + voteCount +
                ", voteAverage=" + voteAverage +
                ", genres=" + genres +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", videos=" + videos +
                '}';
    }
}
