package com.todocine.entities;

import com.todocine.dto.MovieDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "MOVIE")
public class Movie {

    @Id
    private String id;

    private String originalTitle;

    private String title;

    private String posterPath;

    @Column(length = 700)
    private String overview;

    private String releaseDate;

    private Double popularity;

    private Integer voteCount;

    private Double voteAverage;

    private String originalLanguage;


    @OneToMany(mappedBy = "id.movie", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Voto> votosTC = new ArrayList<>();

    private Integer totalVotosTC;

    private Double votosMediaTC;

    public Movie() {
    }

    public Movie(String id) {
        this.id = id;
    }

    public Movie(String id, String originalTitle, String title, String posterPath, String overview, String releaseDate,
                 Double popularity, Integer voteCount, Double voteAverage, String originalLanguage, List<Voto> votosTC,
                 Integer totalVotosTC, Double votosMediaTC) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.originalLanguage = originalLanguage;
        this.votosTC = votosTC;
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
        this.originalLanguage = movieDTO.getOriginalLanguage();
        this.votosTC = movieDTO.getVotos().stream().map(voto ->
                new Voto(new VotoId(new Usuario(voto.getUsuarioId()), new Movie(voto.getMovieId()))))
                .collect(Collectors.toList());
        this.votosMediaTC = movieDTO.getVotosMediaTC();
        this.totalVotosTC = movieDTO.getTotalVotosTC();
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

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public List<Voto> getVotosTC() {
        return votosTC;
    }

    public void setVotosTC(List<Voto> votosTC) {
        this.votosTC = votosTC;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id.equals(movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
                "id='" + id + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", title='" + title + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", popularity=" + popularity +
                ", voteCount=" + voteCount +
                ", voteAverage=" + voteAverage +
                 ", originalLanguage='" + originalLanguage + '\'' +

                '}';
    }
}
