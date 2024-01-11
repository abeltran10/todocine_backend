package com.todocine.dao;

import com.todocine.dto.MovieDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieDAORepo {

    List<MovieDTO> findByUserId(String userId);

}
