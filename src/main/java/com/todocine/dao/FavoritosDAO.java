package com.todocine.dao;

import com.todocine.entities.Favoritos;
import com.todocine.entities.FavoritosId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritosDAO extends JpaRepository<Favoritos, FavoritosId> {

    List<Favoritos> findByIdUsuarioId(Long id);
}
