package com.todocine.dao;

import com.todocine.dto.response.Paginator;
import com.todocine.entities.UsuarioMovie;

import java.util.Map;

public interface UsuarioMovieRepo {

    public Paginator<UsuarioMovie> getUserMoviesByFilter(Long usuarioId, Map<String, String> filters, String orderBy, int limit, int pagina);
}
