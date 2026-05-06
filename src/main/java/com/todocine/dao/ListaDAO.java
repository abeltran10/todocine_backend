package com.todocine.dao;

import com.todocine.entities.Lista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListaDAO extends JpaRepository<Lista, Long> {

    Page<Lista> findByUsuarioId(Long userId, Pageable pageable);
}
