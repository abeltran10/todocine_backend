package com.todocine.dao;

import com.todocine.entities.Ganador;
import com.todocine.entities.GanadorId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GanadorDAO extends JpaRepository<Ganador, GanadorId> {

    List<Ganador> findByIdPremioIdAndIdAnyo(Long premio, Integer anyo);
}
