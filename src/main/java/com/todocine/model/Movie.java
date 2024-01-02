package com.todocine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.todocine.dto.MovieDTO;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {


    private String id;

    private String originalTitle;

    private String posterPath;

    private String overview;

    private Date releaseDate;

    private Integer popularity;

    private Integer voteCount;

    private Double voteAverage;


    private List<Integer> genreIds;

    private String originalLanguage;

    public Movie() {
    }


    public Movie(String id, String originalTitle, String posterPath, String overview, Date releaseDate, Integer popularity, Integer voteCount, Double voteAverage, List<Integer> genreIds, String originalLanguage) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.genreIds = genreIds;
        this.originalLanguage = originalLanguage;
    }

    public Movie(MovieDTO movieDTO) {
        this.id = movieDTO.getId();
        this.originalTitle = movieDTO.getOriginalTitle();
        this.posterPath = movieDTO.getPosterPath();
        this.overview = movieDTO.getOverview();
        this.releaseDate = movieDTO.getReleaseDate();
        this.popularity = movieDTO.getPopularity();
        this.voteCount = movieDTO.getVoteCount();
        this.voteAverage = movieDTO.getVoteAverage();
        this.genreIds = movieDTO.getGenreIds();
        this.originalLanguage = movieDTO.getOriginalLanguage();
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
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

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate=" + releaseDate +
                ", popularity=" + popularity +
                ", voteCount=" + voteCount +
                ", voteAverage=" + voteAverage +
                ", genreIds=" + genreIds +
                ", originalLanguage='" + originalLanguage + '\'' +
                '}';
    }
}
