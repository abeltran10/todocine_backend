package com.todocine.dao;

import com.todocine.entities.VotoComentario;
import com.todocine.entities.VotoComentarioId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoComentarioDAO extends JpaRepository<VotoComentario, VotoComentarioId> {
}
