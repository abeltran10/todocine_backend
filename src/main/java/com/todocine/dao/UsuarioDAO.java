package com.todocine.dao;

import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface UsuarioDAO extends MongoRepository<Usuario, String> {

    Usuario findByUsername(String username);

    List<Movie> findFavoritosById(String id);
}
