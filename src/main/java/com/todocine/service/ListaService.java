package com.todocine.service;

import com.todocine.dto.ListaDTO;
import com.todocine.exceptions.*;
import com.todocine.utils.Paginator;
import jakarta.validation.constraints.NotNull;

public interface ListaService {

    ListaDTO getListaById(Long id);
    ListaDTO createLista(ListaDTO listaDTO);
    ListaDTO updateLista(Long id, ListaDTO listaDTO);
    void deleteLista(Long id);
    Paginator<ListaDTO> getListas(Long userId, Integer page);
    ListaDTO addMovieToList(Long listaId, Long movieId);
    void deleteMovieFromList(Long listaId, Long movieId);
}
