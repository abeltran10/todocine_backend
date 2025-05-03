package com.todocine.dao;

import com.todocine.entities.Movie;
import com.todocine.entities.UsuarioMovie;
import com.todocine.entities.UserMovieId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioMovieDAO extends JpaRepository<UsuarioMovie, UserMovieId> {

    Page<UsuarioMovie> findByIdUsuarioIdAndFavoritos(Long usuarioId, String favoritos, Pageable pageable);

}
