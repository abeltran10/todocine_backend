package com.todocine.dao;

import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UsuarioDAO extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);

}
