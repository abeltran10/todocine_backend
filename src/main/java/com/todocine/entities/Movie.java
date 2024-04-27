package com.todocine.entities;

import com.todocine.dto.MovieDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Document(collection = "Movie")
public class Movie {

    @Id
    private String id;

    private String originalTitle;

    private String title;

    private String posterPath;

    private String overview;

    private String releaseDate;

    private Double popularity;

    private Integer voteCount;

    private Double voteAverage;

    @DocumentReference
    private List<Genre> genreIds;
    private String originalLanguage;

    @DocumentReference
    private List<Video> videos;

    @DocumentReference(lazy = true)
    private List<Usuario> usuarios;

    @DocumentReference
    private List<Premio> premios;

    @DocumentReference
    private List<Voto> votosTC;

    private Integer totalVotosTC;

    private Double votosMediaTC;

    @Version
    private Integer version;

    public Movie() {
    }

    public Movie(String id) {
        this.id = id;
    }

    @PersistenceCreator
    public Movie(String id, String originalTitle, String title, String posterPath, String overview, String releaseDate,
                 Double popularity, Integer voteCount, Double voteAverage, List<Genre> genreIds,
                 String originalLanguage, List<Video> videos, List<Usuario> usuarios, List<Premio> premios, List<Voto> votosTC,
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
        this.genreIds = genreIds;
        this.originalLanguage = originalLanguage;
        this.videos = videos;
        this.usuarios = usuarios;
        this.premios = premios;
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
        this.genreIds = movieDTO.getGenres().stream().map(genre -> new Genre(genre.getId())).collect(Collectors.toList());
        this.originalLanguage = movieDTO.getOriginalLanguage();
        this.videos = movieDTO.getVideos().stream().map(video -> new Video(video)).collect(Collectors.toList());
        this.usuarios = new ArrayList<>();
        this.premios = movieDTO.getPremios().stream().map(premio -> new Premio(premio.getId())).collect(Collectors.toList());
        this.votosTC = movieDTO.getVotos().stream().map(voto -> new Voto(voto.getId())).collect(Collectors.toList());
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

    public List<Genre> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Genre> genreIds) {
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

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Premio> getPremios() {
        return premios;
    }

    public void setPremios(List<Premio> premios) {
        this.premios = premios;
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
                ", genreIds=" + genreIds +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", videos=" + videos +
                ", version=" + version +
                '}';
    }
}
