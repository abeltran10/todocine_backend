package com.todocine.utils.mapper;

import com.todocine.dto.CategoriaDTO;
import com.todocine.entities.Categoria;

public class CategoriaMapper {

    public static CategoriaDTO toDTO(Categoria categoria) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setNombre(categoria.getNombre());

        return categoriaDTO;
    }
}
