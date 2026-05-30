package com.todocine.utils.mapper;

import com.todocine.dto.response.MovieListaDTO;
import com.todocine.entities.Movie;

public class MovieListaMapper {

    public static MovieListaDTO toDTO(Movie movie) {
        MovieListaDTO movieListaDTO = new MovieListaDTO(movie.getId());
        movieListaDTO.setOriginalTitle(movie.getOriginalTitle());
        movieListaDTO.setTitle(movie.getTitle());
        movieListaDTO.setOverview(movie.getOverview());
        movieListaDTO.setPosterPath(movie.getPosterPath());
        movieListaDTO.setReleaseDate(movie.getReleaseDate());

        return movieListaDTO;
    }
}
