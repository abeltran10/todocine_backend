package com.todocine.service.impl;

import com.todocine.dao.GanadorDAO;
import com.todocine.dao.PremioDAO;
import com.todocine.dto.GanadorDTO;
import com.todocine.dto.PremioAnyoDTO;
import com.todocine.dto.PremioDTO;
import com.todocine.entities.Ganador;
import com.todocine.entities.Premio;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.PremioService;
import com.todocine.utils.mapper.GanadorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PremioServiceImpl implements PremioService {

    @Autowired
    private PremioDAO premioDAO;

    @Autowired
    private GanadorDAO ganadorDAO;

    @Override
    @Transactional(readOnly = true)
    public List<GanadorDTO> getPremioByCodigoAnyo(Long id, Integer anyo) throws NotFoudException {
        List<Ganador> premios = ganadorDAO.findByIdPremioIdAndIdAnyo(id, anyo);

        if (premios != null) {
                List<GanadorDTO> premiosDTO = GanadorMapper.toDTOList(premios);
                return premiosDTO;
        }

        throw new NotFoudException("Premio no encontrado");

    }


    @Override
    @Transactional(readOnly = true)
    public List<PremioAnyoDTO> getPremios() throws NotFoudException {
        Map<Long, PremioAnyoDTO> premioMap = new HashMap<>();
        List<Object[]> obj = premioDAO.getPremiosAnyo();

        if (obj != null && !obj.isEmpty()) {
            for (Object[] row : obj) {
              Long id = (Long) row[0];
              String titulo = (String) row[1];
              Integer anyo = ((BigDecimal) row[2]).intValue();

              premioMap.computeIfAbsent(id, k-> new PremioAnyoDTO(id, titulo)).getAnyos().add(anyo);
            }

            return new ArrayList<>(premioMap.values());
        } else {
            throw new NotFoudException("No se han encontrado premios");
        }
    }
}
