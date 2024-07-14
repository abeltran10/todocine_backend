package com.todocine.dao;

import com.todocine.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaDAO extends JpaRepository<Categoria, Long> {
}
