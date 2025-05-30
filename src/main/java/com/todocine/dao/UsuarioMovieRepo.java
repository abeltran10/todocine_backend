package com.todocine.dao;

import com.todocine.entities.UsuarioMovie;
import com.todocine.utils.Paginator;

import java.util.List;

public interface UsuarioMovieRepo {

    public Paginator<UsuarioMovie> getUserMoviesByFilter(Long usuarioId, String vista, int limit, int offset);
}
