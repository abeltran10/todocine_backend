package com.todocine.service.impl;

import com.todocine.dao.PremioDAO;
import com.todocine.dto.PremioDTO;
import com.todocine.model.Premio;
import com.todocine.service.PremioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PremioServiceImpl implements PremioService {

    @Autowired
    private PremioDAO premioDAO;

    @Override
    public Premio getPremioById(String id) {
        PremioDTO premioDTO = premioDAO.findById(id).get();

        Premio premio = new Premio(premioDTO);
        return premio;
    }
}
