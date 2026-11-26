package com.todocine.dao;

import com.todocine.entities.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioDAO extends JpaRepository<Comentario, Long> {

    List<Comentario> findByMovieIdAndParentIsNullOrderByFechaCreacionDesc(Long movieId);
    List<Comentario> findByParentId(Long id);
}
