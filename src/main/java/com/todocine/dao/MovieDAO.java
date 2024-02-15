package com.todocine.dao;

import com.todocine.dto.MovieDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieDAO extends MongoRepository<MovieDTO, String> {


    List<MovieDTO> findByUsuariosId(String userId);
}
