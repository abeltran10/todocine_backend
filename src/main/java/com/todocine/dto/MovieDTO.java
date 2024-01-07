package com.todocine.dto;

import com.todocine.model.Movie;
import com.todocine.model.Video;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "Movie")
public class MovieDTO {

    @Id
    private String id;

    private String originalTitle;

    private String title;

    private String posterPath;

    private String overview;

    private String releaseDate;

    private Integer popularity;

    private Integer voteCount;

    private Double voteAverage;

    @DocumentReference
    private List<GenreDTO> genreIds;
    private String originalLanguage;

    @DocumentReference
    private List<VideosDTO> videos;

    @Version
    private Integer version;

    public MovieDTO() {
    }

    @PersistenceCreator
    public MovieDTO(String id, String originalTitle, String title, String posterPath, String overview, String releaseDate,
                    Integer popularity, Integer voteCount, Double voteAverage,
                    List<GenreDTO> genreIds, String originalLanguage, List<VideosDTO> videos) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.genreIds = genreIds;
        this.originalLanguage = originalLanguage;
        this.videos = videos;
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
        this.genreIds = movie.getGenres().stream().map(genre -> new GenreDTO(genre)).collect(Collectors.toList());
        this.originalLanguage = movie.getOriginalLanguage();
        this.videos = movie.getVideos().stream().map(video -> new VideosDTO(video)).collect(Collectors.toList());
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

    public List<GenreDTO> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<GenreDTO> genreIds) {
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

    public List<VideosDTO> getVideos() {
        return videos;
    }

    public void setVideos(List<VideosDTO> videos) {
        this.videos = videos;
    }
}
