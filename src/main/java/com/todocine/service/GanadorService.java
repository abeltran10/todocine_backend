package com.todocine.service;

import com.todocine.dto.response.GanadorDTO;

import com.todocine.dto.request.GanadorReqDTO;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.ConflictException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.utils.Paginator;

public interface GanadorService {

    Paginator<GanadorDTO> getGanadoresByPremioIdAnyo(Long id, Integer anyo, Integer page);

    GanadorDTO insertGanador(GanadorReqDTO ganadorReqDTO) throws ConflictException, NotFoudException, BadGatewayException;
}
