package com.todocine.dao;

import com.todocine.dto.VotoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VotoDAO extends MongoRepository<VotoDTO, String> {
}
