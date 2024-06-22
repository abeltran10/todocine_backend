package com.todocine.service;

import com.todocine.dto.PremioDTO;
import com.todocine.exceptions.NotFoudException;

public interface PremioService {

    PremioDTO getPremioByCodigo(Integer codigo) throws NotFoudException;

}
