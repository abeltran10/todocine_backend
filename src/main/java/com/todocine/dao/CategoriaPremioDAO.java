package com.todocine.dao;

import com.todocine.entities.Categoria;
import com.todocine.entities.CategoriaPremio;
import com.todocine.entities.CategoriaPremioId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaPremioDAO extends JpaRepository<CategoriaPremio, CategoriaPremioId> {
    List<CategoriaPremio> findById_Premio_Id(Long premioId);
}
