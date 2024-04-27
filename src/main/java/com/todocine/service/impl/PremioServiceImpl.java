package com.todocine.service.impl;

import com.todocine.dao.PremioDAO;
import com.todocine.entities.Premio;
import com.todocine.dto.PremioDTO;
import com.todocine.service.PremioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PremioServiceImpl implements PremioService {

    @Autowired
    private PremioDAO premioDAO;

    @Override
    public PremioDTO getPremioById(String id) {
        Premio premio = premioDAO.findById(id).get();

        PremioDTO premioDTO = new PremioDTO(premio);
        return premioDTO;
    }
}
