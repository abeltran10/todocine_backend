package com.todocine.dao;

import com.todocine.entities.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActividadDAO extends JpaRepository<Actividad, Long> {
}
