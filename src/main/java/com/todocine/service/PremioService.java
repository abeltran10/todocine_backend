package com.todocine.service;

import com.todocine.dto.GanadorDTO;

import com.todocine.dto.PremioAnyoDTO;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;

import java.util.List;

public interface PremioService {

    List<GanadorDTO> getPremioByCodigoAnyo(Integer codigo, Integer anyo) throws NotFoudException;
    List<PremioAnyoDTO> getPremios() throws NotFoudException;

}
