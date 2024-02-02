package com.todocine.dao;

import com.todocine.dto.MovieDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieDAO extends MongoRepository<MovieDTO, String>, MovieDAORepo {


}
