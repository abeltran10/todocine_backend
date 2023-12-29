package com.todocine.service;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface MovieService {

    public ResponseEntity getMovieByName(String name);
}
