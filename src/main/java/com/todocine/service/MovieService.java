package com.todocine.service;

import com.todocine.dto.response.ComentarioDTO;
import com.todocine.dto.response.MovieDetailDTO;
import com.todocine.dto.response.Paginator;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public interface MovieService {

    MovieDetailDTO getMovieDetailById(Long id);

    Paginator getMovies(Map<String, String> filters, Integer pagina);

    List<ComentarioDTO> getComentariosByMovie(Long id);
}
