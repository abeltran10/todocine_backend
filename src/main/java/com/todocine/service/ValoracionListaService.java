package com.todocine.service;

import com.todocine.dto.request.ValoracionListaReqDTO;
import com.todocine.dto.response.ValoracionListaDTO;

import java.util.List;

public interface ValoracionListaService {

    ValoracionListaDTO guardarValoracionLista(Long listaId, ValoracionListaReqDTO valoracionListaReqDTO);

    List<ValoracionListaDTO> getListaValoraciones(Long listaId);
}
