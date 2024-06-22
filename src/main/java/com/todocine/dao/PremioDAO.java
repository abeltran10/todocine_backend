package com.todocine.dao;

import com.todocine.entities.Premio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PremioDAO extends MongoRepository<Premio, String> {

    Premio findByCodigo(Integer codigo);
}
