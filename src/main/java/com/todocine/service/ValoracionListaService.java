package com.todocine.service;

import com.todocine.dto.request.ValoracionListaDTO;
import com.todocine.dto.response.ListaDTO;
import com.todocine.dto.response.ValoracionDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface ValoracionListaService {

    ValoracionDTO updateValoracionLista(Long listaId, Long usuarioId, ValoracionListaDTO valoracionListaDTO);

    List<ValoracionDTO> getListaValoraciones(Long listaId);
}
