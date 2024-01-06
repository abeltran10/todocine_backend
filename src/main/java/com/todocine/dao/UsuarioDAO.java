package com.todocine.dao;

import com.todocine.dto.UsuarioDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface UsuarioDAO extends MongoRepository<UsuarioDTO, String> {

    UsuarioDTO findByUsername(String username);

}
