package com.todocine.service;

import com.todocine.dto.GanadorDTO;

import com.todocine.dto.PremioAnyoDTO;
import com.todocine.exceptions.NotFoudException;
import com.todocine.utils.Paginator;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PremioService {

    Paginator<GanadorDTO> getPremioByCodigoAnyo(Long id, Integer anyo, Integer page) throws NotFoudException;
    List<PremioAnyoDTO> getPremioAnyos() throws NotFoudException;

}
