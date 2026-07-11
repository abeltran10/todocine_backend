package com.todocine.service.impl;

import com.todocine.dao.CategoriaPremioDAO;
import com.todocine.dto.response.CategoriaDTO;
import com.todocine.entities.CategoriaPremio;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.CategoriaPremioService;
import com.todocine.utils.mapper.CategoriaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.todocine.configuration.Constants.PREMIO_NOTFOUND;


@Service
public class CategoriaPremioServiceImpl implements CategoriaPremioService {

    @Autowired
    private CategoriaPremioDAO categoriaPremioDAO;

    @Override
    public List<CategoriaDTO> getCategorias(Long premioId) {
        List<CategoriaPremio> categoriasPremio = categoriaPremioDAO.findById_Premio_Id(premioId);

        if (categoriasPremio.isEmpty())
            throw new NotFoudException(PREMIO_NOTFOUND);

        return categoriasPremio.stream().map(cp -> CategoriaMapper.toDTO(cp.getId().getCategoria())).toList();
    }
}
