package com.todocine.dao;

import com.todocine.entities.Voto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VotoDAO extends MongoRepository<Voto, String> {
}
