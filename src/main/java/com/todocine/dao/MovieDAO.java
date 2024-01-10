package com.todocine.dao;

import com.todocine.dto.MovieDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MovieDAO extends MongoRepository<MovieDTO, String> {

}
