package com.todocine.utils.mapper;

import com.todocine.dto.PremioDTO;
import com.todocine.entities.Premio;

public class PremioMapper {

    public static Premio toEntity(PremioDTO premioDTO) {
        Premio premio = new Premio();

        premio.setId(premioDTO.getId());
        premio.setTitulo(premioDTO.getTitulo());
        premio.setCodigo(premioDTO.getCodigo());

        return premio;
    }

    public static PremioDTO toDTO(Premio premio) {
        PremioDTO premioDTO = new PremioDTO(premio.getId());
        premioDTO.setTitulo(premio.getTitulo());
        premioDTO.setCodigo(premio.getCodigo());

        return premioDTO;
    }
}
