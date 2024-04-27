package com.todocine.dao;

import com.todocine.entities.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UsuarioDAO extends MongoRepository<Usuario, String> {

    Usuario findByUsername(String username);

}
