package com.todocine.dao;

import com.todocine.entities.Lista;
import com.todocine.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ListaDAO extends JpaRepository<Lista, Long>, ListaRepo {

    Page<Lista> findByUsuarioId(Long userId, Pageable pageable);

    Page<Lista> findByPublica(String publica, Pageable pageable);

}
