package com.todocine.dao;

import com.todocine.entities.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieDAO extends MongoRepository<Movie, String> {


    List<Movie> findByUsuariosId(String userId);
}
