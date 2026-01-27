package com.todocine.dao;

import com.todocine.entities.Premio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PremioDAO extends JpaRepository<Premio, Long> {

}
