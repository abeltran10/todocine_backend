package com.todocine.service.impl;

import com.todocine.dao.PremioDAO;
import com.todocine.entities.Premio;
import com.todocine.dto.PremioDTO;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.PremioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PremioServiceImpl implements PremioService {

    @Autowired
    private PremioDAO premioDAO;

    @Override
    @Transactional(readOnly = true)
    public PremioDTO getPremioByCodigo(Integer codigo) throws NotFoudException {
        Premio premio = premioDAO.findByCodigo(codigo);

        if (premio != null) {
            PremioDTO premioDTO = new PremioDTO(premio);
            return premioDTO;
        }

        throw new NotFoudException("Premio no encontrado");


    }
}
