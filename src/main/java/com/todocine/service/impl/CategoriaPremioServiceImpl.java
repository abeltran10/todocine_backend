package com.todocine.service.impl;

import com.todocine.dao.CategoriaPremioDAO;
import com.todocine.dto.CategoriaDTO;
import com.todocine.entities.CategoriaPremio;
import com.todocine.service.CategoriaPremioService;
import com.todocine.utils.mapper.CategoriaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoriaPremioServiceImpl implements CategoriaPremioService {

    @Autowired
    private CategoriaPremioDAO categoriaPremioDAO;

    @Override
    public List<CategoriaDTO> getCategorias(Long premioId) {
        List<CategoriaPremio> categoriasPremio = categoriaPremioDAO.findById_Premio_Id(premioId);

        return categoriasPremio.stream().map(cp -> CategoriaMapper.toDTO(cp.getId().getCategoria())).toList();
    }
}
