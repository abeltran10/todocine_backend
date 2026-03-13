package com.todocine.service;

import com.todocine.dto.response.CategoriaDTO;

import java.util.List;

public interface CategoriaPremioService {

    public List<CategoriaDTO> getCategorias(Long premioId);
}
