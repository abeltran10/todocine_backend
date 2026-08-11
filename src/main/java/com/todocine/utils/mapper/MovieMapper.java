package com.todocine.utils.mapper;

import com.todocine.dto.response.MovieDTO;
import com.todocine.entities.Movie;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MovieMapper {

    public static Movie toEntity(MovieDTO movieDTO) {
        Movie movie = new Movie();

        movie.setId(movieDTO.getId());
        movie.setOriginalTitle(movieDTO.getOriginalTitle());
        movie.setTitle(movieDTO.getTitle());
        movie.setPosterPath(movieDTO.getPosterPath());
        movie.setOverview(movieDTO.getOverview());

        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fecha = LocalDate.parse(movieDTO.getReleaseDate(), formateador);
        movie.setReleaseDate(fecha);

        movie.setPopularity(movieDTO.getPopularity());
        movie.setVoteCount(movieDTO.getVoteCount());
        movie.setVoteAverage(movieDTO.getVoteAverage());
        movie.setOriginalLanguage(movieDTO.getOriginalLanguage());
        movie.setVotosMediaTC(movieDTO.getVotosMediaTC());
        movie.setTotalVotosTC(movieDTO.getTotalVotosTC());

        return movie;
    }

    public static MovieDTO toDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setId(movie.getId());
        movieDTO.setOriginalTitle(movie.getOriginalTitle());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setPosterPath(movie.getPosterPath());
        movieDTO.setOverview(movie.getOverview());

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fecha = movie.getReleaseDate().format(formato);
        movieDTO.setReleaseDate(fecha);

        movieDTO.setPopularity(movie.getPopularity());
        movieDTO.setVoteCount(movie.getVoteCount());
        movieDTO.setVoteAverage(movie.getVoteAverage());
        movieDTO.setOriginalLanguage(movie.getOriginalLanguage());
        movieDTO.setVotosMediaTC(movie.getVotosMediaTC());
        movieDTO.setTotalVotosTC(movie.getTotalVotosTC());

        return movieDTO;
    }

}
