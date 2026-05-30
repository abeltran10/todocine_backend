package com.todocine.service;

import com.todocine.dto.request.ListaReqDTO;
import com.todocine.dto.response.ListaDTO;
import com.todocine.dto.response.MovieListaDTO;
import com.todocine.utils.Paginator;
import jakarta.validation.constraints.NotNull;

public interface ListaService {

    ListaDTO getListaById(Long id);

    ListaDTO createLista(ListaReqDTO listaDTO);

    ListaDTO updateLista(Long id, ListaReqDTO listaDTO);

    void deleteLista(Long id);

    Paginator<ListaDTO> getListasUser(Long usuarioId, Integer page);

    ListaDTO addMovieToList(Long listaId, Long movieId);

    void deleteMovieFromList(Long listaId, Long movieId);

    Paginator<ListaDTO> getListasPublicas(Integer page);

    Paginator<MovieListaDTO> getMoviesByLista(Long listaId, Integer pagina);
}
