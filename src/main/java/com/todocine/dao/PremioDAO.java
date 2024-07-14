package com.todocine.dao;

import com.todocine.entities.Premio;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PremioDAO extends JpaRepository<Premio, Long> {

    Premio findByCodigo(Integer codigo);
}
