package com.todocine.dao;

import com.todocine.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioDAO extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);

}
