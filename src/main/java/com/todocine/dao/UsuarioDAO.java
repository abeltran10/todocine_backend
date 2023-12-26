package com.todocine.dao;

import com.todocine.dto.UsuarioDTO;
import com.todocine.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioDAO extends MongoRepository<UsuarioDTO, String> {

    public UsuarioDTO findUsuarioDTOByUsername(String username);

    public UsuarioDTO findUsuarioDTOById(String id);

}
