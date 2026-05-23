package com.todocine.utils.mapper;

import com.todocine.dto.response.ListaDTO;
import com.todocine.dto.response.MovieDTO;
import com.todocine.dto.response.MovieListaDTO;
import com.todocine.entities.Lista;

import java.util.List;

public class ListaMapper {

    public static ListaDTO toDTO(Lista lista) {
        ListaDTO listaDTO = new ListaDTO(lista.getId());
        listaDTO.setNombre(lista.getNombre());
        listaDTO.setDescripcion(lista.getDescripcion());

        List<MovieListaDTO> movieDTOList = lista.getMovies().stream()
                        .map(movie -> {
                            MovieListaDTO movieListaDTO = new MovieListaDTO(movie.getId());
                            movieListaDTO.setOriginalTitle(movie.getOriginalTitle());
                            movieListaDTO.setTitle(movie.getTitle());
                            movieListaDTO.setOverview(movie.getOverview());
                            movieListaDTO.setPosterPath(movie.getPosterPath());
                            movieListaDTO.setReleaseDate(movie.getReleaseDate());

                            return movieListaDTO;
                        }).toList();
        listaDTO.setMovies(movieDTOList);

        listaDTO.setUsername(lista.getUsuario().getUsername());
        listaDTO.setPublica("S".equals(lista.getPublica()));

        return listaDTO;
    }

}
