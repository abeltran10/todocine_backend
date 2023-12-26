package com.todocine.dao;

import com.todocine.dto.UsuarioDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioDAO extends MongoRepository<UsuarioDTO, String> {

    public UsuarioDTO findUsuarioDTOByUsername(String username);

    public UsuarioDTO findUsuarioDTOById(String id);

}
