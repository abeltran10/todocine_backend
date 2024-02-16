package com.todocine.dao;

import com.todocine.dto.PremioDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PremioDAO extends MongoRepository<PremioDTO, String> {
}
