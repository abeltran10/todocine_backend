package com.todocine.dao;

import com.todocine.entities.Movie;
import com.todocine.utils.Paginator;

public interface ListaRepo {

    Paginator<Movie> getMoviesByListaId(Long listaId, String orderBy, String direction, int limit, int offset);
}
