package com.todocine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.todocine.dto.MovieDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {


    @JsonProperty("id")
    @NotBlank
    private String id;

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
    @NotBlank
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
    private List<Genre> genres;

    @JsonProperty("original_language")
    @NotBlank
    private String originalLanguage;

    @JsonProperty("videos")
    @NotNull
    private List<Video> videos;
    @JsonProperty("votos")
    @NotNull
    private List<Voto> votos;

    @JsonProperty("total_votos_TC")
    @NotNull
    private Integer totalVotosTC;

    @JsonProperty("votos_media_TC")
    @NotNull
    private Double votosMediaTC;

    public Movie() {
    }

    public Movie(String id) {
        this.id = id;
    }

    public Movie(String id, String originalTitle, String title, String posterPath, String overview, String releaseDate,
                 Double popularity, Integer voteCount, Double voteAverage, List<Genre> genres, String originalLanguage,
                 List<Video> videos, List<Voto> votos, Integer totalVotosTC, Double votosMediaTC) {
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
        this.votos = votos;
        this.totalVotosTC = totalVotosTC;
        this.votosMediaTC = votosMediaTC;
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
        this.genres = movieDTO.getGenreIds().stream().map(genreDTO ->  new Genre(genreDTO)).collect(Collectors.toList());
        this.originalLanguage = movieDTO.getOriginalLanguage();
        this.videos = movieDTO.getVideos().stream().map(videoDTO -> new Video(videoDTO)).collect(Collectors.toList());
        this.votos = movieDTO.getVotosTC().stream().map(votoDTO -> new Voto(votoDTO)).collect(Collectors.toList());
        this.votosMediaTC = movieDTO.getVotosMediaTC();
        this.totalVotosTC = movieDTO.getTotalVotosTC();
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

        if (map.containsKey("genres"))
            this.genres = ((List<Map<String, Object>>) map.get("genres")).stream().map(genres -> new Genre(genres)).collect(Collectors.toList());
        else if (map.containsKey("genre_ids"))
            this.genres = ((List<Integer>) map.get("genre_ids")).stream().map(genres -> new Genre(String.valueOf(genres))).collect(Collectors.toList());
        else
            this.genres = new ArrayList<>();

        this.originalLanguage = (String) map.get("original_language");

        Map<String, Object> objectMap = (map.containsKey("videos") && !(map.get("videos").equals("false")))
                ? (Map<String, Object>) map.get("videos") : null;

        this.videos = (objectMap != null) ? ((List<Map<String, Object>>) objectMap.get("results")).stream()
                .map(item -> new Video(item)).collect(Collectors.toList()) : new ArrayList<>();

        this.votos = new ArrayList<>();
        this.totalVotosTC = 0;
        this.votosMediaTC = 0D;
    }

    public Movie(String id, String originalTitle) {
        this.id = id;
        this.originalTitle = originalTitle;
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

    public List<Voto> getVotos() {
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
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
