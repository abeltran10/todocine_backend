package com.todocine.service;

import com.todocine.dto.MovieDTO;
import com.todocine.dto.VotoDTO;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.NotFoudException;

public interface VotoService {

    MovieDTO updateVote(String movieId, VotoDTO votoDTO) throws BadGatewayException, NotFoudException;
}
