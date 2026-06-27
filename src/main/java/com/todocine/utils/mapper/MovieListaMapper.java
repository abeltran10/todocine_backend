package com.todocine.utils.mapper;

import com.todocine.dto.response.MovieListaDTO;
import com.todocine.entities.Movie;

import java.time.format.DateTimeFormatter;

public class MovieListaMapper {

    public static MovieListaDTO toDTO(Movie movie) {
        MovieListaDTO movieListaDTO = new MovieListaDTO(movie.getId());
        movieListaDTO.setOriginalTitle(movie.getOriginalTitle());
        movieListaDTO.setTitle(movie.getTitle());
        movieListaDTO.setOverview(movie.getOverview());
        movieListaDTO.setPosterPath(movie.getPosterPath());

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fecha = movie.getReleaseDate().format(formato);
        movieListaDTO.setReleaseDate(fecha);

        return movieListaDTO;
    }
}
