package com.todocine.dto;

import com.todocine.model.Movie;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "Movie")
public class MovieDTO {

    @Id
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

    @Version
    private Integer version;

    public MovieDTO() {
    }

    @PersistenceCreator
    public MovieDTO(String id, String originalTitle, String posterPath, String overview, Date releaseDate,
                    Integer popularity, Integer voteCount, Double voteAverage,
                    List<Integer> genreIds, String originalLanguage) {
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

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.originalTitle = movie.getOriginalTitle();
        this.posterPath = movie.getPosterPath();
        this.overview = movie.getOverview();
        this.releaseDate = movie.getReleaseDate();
        this.popularity = movie.getPopularity();
        this.voteCount = movie.getVoteCount();
        this.voteAverage = movie.getVoteAverage();
        this.genreIds = movie.getGenreIds();
        this.originalLanguage = movie.getOriginalLanguage();
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
