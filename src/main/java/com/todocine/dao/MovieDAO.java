package com.todocine.dao;

import com.todocine.dto.MovieDTO;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface MovieDAO {

    String getMoviesByName(String name, Integer pagina) throws IOException;
}
