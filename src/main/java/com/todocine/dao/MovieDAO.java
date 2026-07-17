package com.todocine.dao;

import com.todocine.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieDAO extends JpaRepository<Movie, Long> {

    @Query("SELECT m.id FROM Movie m")
    Page<Long> findAllIds(Pageable pageable);
}
