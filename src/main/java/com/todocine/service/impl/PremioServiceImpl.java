package com.todocine.service.impl;

import com.todocine.dao.GanadorDAO;
import com.todocine.dao.PremioDAO;
import com.todocine.dto.GanadorDTO;
import com.todocine.dto.PremioDTO;
import com.todocine.entities.Ganador;
import com.todocine.entities.Premio;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.PremioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PremioServiceImpl implements PremioService {

    @Autowired
    private PremioDAO premioDAO;

    @Autowired
    private GanadorDAO ganadorDAO;

    @Override
    @Transactional(readOnly = true)
    public List<GanadorDTO> getPremioByCodigoAnyo(Integer codigo, Integer anyo) throws NotFoudException {
        Premio premio = premioDAO.findByCodigo(codigo);

        if (premio != null) {
            List<Ganador> premios = ganadorDAO.findByIdPremioIdAndIdAnyo(premio.getId(), anyo);

            if (premios != null) {
                List<GanadorDTO> premiosDTO = premios.stream().map(GanadorDTO::new).toList();
                return premiosDTO;
            }
        }

        throw new NotFoudException("Premio no encontrado");

    }
}
