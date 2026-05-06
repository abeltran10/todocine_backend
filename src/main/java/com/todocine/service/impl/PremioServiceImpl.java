package com.todocine.service.impl;

import com.todocine.dao.GanadorDAO;
import com.todocine.dao.PremioDAO;
import com.todocine.dto.response.PremioDTO;
import com.todocine.entities.Premio;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.PremioService;
import com.todocine.utils.mapper.PremioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.todocine.configuration.Constants.PREMIO_NOTFOUND;

@Service
public class PremioServiceImpl implements PremioService {

    @Autowired
    private PremioDAO premioDAO;

    @Autowired
    private GanadorDAO ganadorDAO;

    @Override
    public List<PremioDTO> getPremios() {
        List<Premio> premios = premioDAO.findAll();

        return premios.stream().map(PremioMapper::toDTO).toList();
    }

    @Override
    public PremioDTO getPremioById(Long id) {
        Premio premio = premioDAO.findById(id).orElse(null);

        if (premio != null) {
            List<Integer> anyos = ganadorDAO.findAnyosByPremioId(id);

            PremioDTO premioDTO = PremioMapper.toDTO(premio);
            premioDTO.setAnyos(anyos);

            return premioDTO;
        } else {
            throw new NotFoudException(PREMIO_NOTFOUND);
        }
    }


}
