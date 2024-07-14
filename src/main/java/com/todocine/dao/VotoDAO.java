package com.todocine.dao;

import com.todocine.entities.Voto;
import com.todocine.entities.VotoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoDAO extends JpaRepository<Voto, VotoId> {
}
