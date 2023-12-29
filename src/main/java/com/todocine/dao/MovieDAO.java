package com.todocine.dao;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface MovieDAO {


    ResponseEntity searchMovie(String name) throws IOException;
}
