package com.todocine.service;

import com.todocine.dto.GanadorDTO;

import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.ConflictException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.utils.Paginator;

public interface GanadorService {

    Paginator<GanadorDTO> getGanadoresByPremioIdAnyo(Long id, Integer anyo, Integer page);

    GanadorDTO insertGanador(GanadorDTO ganadorDTO) throws ConflictException, NotFoudException, BadGatewayException;
}
