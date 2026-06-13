package com.todocine.dao;

import com.todocine.entities.Lista;
import com.todocine.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ListaDAO extends JpaRepository<Lista, Long> {

    Page<Lista> findByUsuarioId(Long userId, Pageable pageable);

    Page<Lista> findByPublica(String publica, Pageable pageable);

    @Query("SELECT m FROM Lista l JOIN l.movies m WHERE l.id = :listaId ORDER BY m.title ASC")
    Page<Movie> findMovieByLista(@Param("listaId") Long listaId, Pageable pageable);
}
