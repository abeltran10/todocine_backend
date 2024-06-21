package com.todocine.service;

import com.todocine.dto.PremioDTO;
import com.todocine.exceptions.NotFoudException;

public interface PremioService {

    PremioDTO getPremioById(String id) throws NotFoudException;

}
