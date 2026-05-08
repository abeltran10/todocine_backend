package com.todocine.dao;

import com.todocine.entities.UserMovieId;
import com.todocine.entities.UsuarioMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioMovieDAO extends JpaRepository<UsuarioMovie, UserMovieId> {

}
