package com.todocine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.todocine.entities.Movie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO {


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
    private List<GenreDTO> genreDTOS;

    @JsonProperty("original_language")
    @NotBlank
    private String originalLanguage;

    @JsonProperty("videos")
    @NotNull
    private List<VideoDTO> videoDTOS;

    @JsonProperty("votos")
    @NotNull
    private List<VotoDTO> votoDTOS;

    @JsonProperty("total_votos_TC")
    @NotNull
    private Integer totalVotosTC;

    @JsonProperty("votos_media_TC")
    @NotNull
    private Double votosMediaTC;

    public MovieDTO() {
    }

    public MovieDTO(String id) {
        this.id = id;
    }

    public MovieDTO(String id, String originalTitle, String title, String posterPath, String overview, String releaseDate,
                    Double popularity, Integer voteCount, Double voteAverage, List<GenreDTO> genreDTOS, String originalLanguage,
                    List<VideoDTO> videoDTOS, List<VotoDTO> votoDTOS, Integer totalVotosTC, Double votosMediaTC) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.genreDTOS = genreDTOS;
        this.originalLanguage = originalLanguage;
        this.videoDTOS = videoDTOS;
        this.votoDTOS = votoDTOS;
        this.totalVotosTC = totalVotosTC;
        this.votosMediaTC = votosMediaTC;
    }

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.originalTitle = movie.getOriginalTitle();
        this.title = movie.getTitle();
        this.posterPath = movie.getPosterPath();
        this.overview = movie.getOverview();
        this.releaseDate = movie.getReleaseDate();
        this.popularity = movie.getPopularity();
        this.voteCount = movie.getVoteCount();
        this.voteAverage = movie.getVoteAverage();
        this.originalLanguage = movie.getOriginalLanguage();
        this.votoDTOS = movie.getVotosTC().stream().map(voto -> new VotoDTO(voto)).collect(Collectors.toList());
        this.votosMediaTC = movie.getVotosMediaTC();
        this.totalVotosTC = movie.getTotalVotosTC();
    }

    public MovieDTO(String id, String originalTitle, String posterPath) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
    }

    public MovieDTO(Map<String, Object> map) {
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
            this.genreDTOS = ((List<Map<String, Object>>) map.get("genres")).stream().map(genres -> new GenreDTO(genres)).collect(Collectors.toList());
        else if (map.containsKey("genre_ids"))
            this.genreDTOS = ((List<Integer>) map.get("genre_ids")).stream().map(genres -> new GenreDTO(String.valueOf(genres))).collect(Collectors.toList());
        else
            this.genreDTOS = new ArrayList<>();

        this.originalLanguage = (String) map.get("original_language");

        Map<String, Object> objectMap = (map.containsKey("videos") && !(map.get("videos").equals("false")))
                ? (Map<String, Object>) map.get("videos") : null;

        this.videoDTOS = (objectMap != null) ? ((List<Map<String, Object>>) objectMap.get("results")).stream()
                .map(item -> new VideoDTO(item)).collect(Collectors.toList()) : new ArrayList<>();

        this.votoDTOS = new ArrayList<>();
        this.totalVotosTC = 0;
        this.votosMediaTC = 0D;
    }

    public MovieDTO(String id, String originalTitle) {
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

    public List<GenreDTO> getGenres() {
        return genreDTOS;
    }

    public void setGenres(List<GenreDTO> genreDTOS) {
        this.genreDTOS = genreDTOS;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public List<VideoDTO> getVideos() {
        return videoDTOS;
    }

    public void setVideos(List<VideoDTO> videoDTOS) {
        this.videoDTOS = videoDTOS;
    }

    public List<VotoDTO> getVotos() {
        return votoDTOS;
    }

    public void setVotos(List<VotoDTO> votoDTOS) {
        this.votoDTOS = votoDTOS;
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
                ", genres=" + genreDTOS +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", videos=" + videoDTOS +
                '}';
    }
}
