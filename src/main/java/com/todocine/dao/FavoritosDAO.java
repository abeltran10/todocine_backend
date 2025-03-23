package com.todocine.dao;

import com.todocine.entities.Favoritos;
import com.todocine.entities.FavoritosId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritosDAO extends JpaRepository<Favoritos, FavoritosId> {

    Page<Favoritos> findByIdUsuarioId(Long id, Pageable pageable);
}
