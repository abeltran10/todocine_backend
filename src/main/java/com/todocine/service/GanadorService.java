package com.todocine.service;

import com.todocine.dto.GanadorDTO;

import com.todocine.dto.GanadorPKDTO;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.utils.Paginator;

import java.io.IOException;

public interface GanadorService {

    Paginator<GanadorDTO> getGanadoresByPremioIdAnyo(Long id, Integer anyo, Integer page) throws NotFoudException;

    GanadorDTO insertGanador(GanadorPKDTO ganadorPKDTO) throws BadRequestException, BadGatewayException;
}
