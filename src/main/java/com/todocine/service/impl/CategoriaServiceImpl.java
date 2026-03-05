package com.todocine.service.impl;

import com.todocine.dao.CategoriaDAO;
import com.todocine.dto.CategoriaDTO;
import com.todocine.entities.Categoria;
import com.todocine.service.CategoriaService;
import com.todocine.utils.mapper.CategoriaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaDAO categoriaDAO;

    @Override
    public List<CategoriaDTO> getCategorias() {
        List<Categoria> categorias = categoriaDAO.findAll();

        return categorias.stream().map(CategoriaMapper::toDTO).toList();
    }
}
