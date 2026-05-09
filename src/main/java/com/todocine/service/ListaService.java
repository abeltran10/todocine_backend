package com.todocine.service;

import com.todocine.dto.ListaDTO;
import com.todocine.dto.request.ListaReqDTO;
import com.todocine.utils.Paginator;

public interface ListaService {

    ListaDTO getListaById(Long id);
    ListaDTO createLista(ListaReqDTO listaDTO);
    ListaDTO updateLista(Long id, ListaReqDTO listaDTO);
    void deleteLista(Long id);
    Paginator<ListaDTO> getListas(Long userId, Integer page);
    ListaDTO addMovieToList(Long listaId, Long movieId);
    void deleteMovieFromList(Long listaId, Long movieId);
}
