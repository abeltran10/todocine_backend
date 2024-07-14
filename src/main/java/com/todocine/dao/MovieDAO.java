package com.todocine.dao;

import com.todocine.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieDAO extends JpaRepository<Movie, String> {

}
