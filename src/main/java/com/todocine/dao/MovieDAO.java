package com.todocine.dao;

import com.todocine.dto.MovieDTO;
import com.todocine.dto.UsuarioDTO;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MovieDAO extends MongoRepository<MovieDTO, String>, MovieDAORepo {


}
