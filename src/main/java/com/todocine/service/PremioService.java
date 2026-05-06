package com.todocine.service;

import com.todocine.dto.response.PremioDTO;
import com.todocine.exceptions.NotFoudException;

import java.util.List;

public interface PremioService {

    List<PremioDTO> getPremios();
    PremioDTO getPremioById(Long id);
}
