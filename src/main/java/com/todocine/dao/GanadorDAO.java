package com.todocine.dao;

import com.todocine.entities.Ganador;
import com.todocine.entities.GanadorId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GanadorDAO extends JpaRepository<Ganador, GanadorId> {

    Page<Ganador> findByIdPremioIdAndIdAnyo(Long premio, Integer anyo, Pageable pageable);
}
