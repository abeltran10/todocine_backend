package com.todocine.dao;

import com.todocine.entities.UsuarioMovie;
import com.todocine.utils.Paginator;

import java.util.Map;

public interface UsuarioMovieRepo {

    public Paginator<UsuarioMovie> getUserMoviesByFilter(Long usuarioId, Map<String, String> filters, int limit, int offset);
}
