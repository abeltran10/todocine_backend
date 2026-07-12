package com.todocine.dao;

import com.todocine.dto.response.Paginator;
import com.todocine.entities.Movie;

public interface ListaRepo {

    Paginator<Movie> getMoviesByListaId(Long listaId, String orderBy, String direction, int limit, int pagina);
}
