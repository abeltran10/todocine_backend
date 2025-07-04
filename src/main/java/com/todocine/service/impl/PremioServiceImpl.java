package com.todocine.service.impl;

import com.todocine.dao.GanadorDAO;
import com.todocine.dao.PremioDAO;
import com.todocine.dto.GanadorDTO;
import com.todocine.dto.MovieDetailDTO;
import com.todocine.dto.PremioAnyoDTO;
import com.todocine.dto.PremioDTO;
import com.todocine.entities.Ganador;
import com.todocine.entities.Premio;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.PremioService;
import com.todocine.utils.Paginator;
import com.todocine.utils.mapper.GanadorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.todocine.configuration.Constants.PREMIOS_NOTFOUND;
import static com.todocine.configuration.Constants.PREMIO_NOTFOUND;

@Service
public class PremioServiceImpl implements PremioService {

    @Autowired
    private PremioDAO premioDAO;

    @Autowired
    private GanadorDAO ganadorDAO;

    @Override
    @Transactional(readOnly = true)
    public Paginator<GanadorDTO> getPremioByCodigoAnyo(Long id, Integer anyo, Integer page) throws NotFoudException {
        Paginator<GanadorDTO> paginator = new Paginator<>();
        Pageable pageable = PageRequest.of(page - 1, 21);

        Page<Ganador> premios = ganadorDAO.findByIdPremioIdAndIdAnyo(id, anyo, pageable);

        if (premios.hasContent()) {
                List<GanadorDTO> premiosDTO = premios.getContent().stream()
                        .map(GanadorMapper::toDTO)
                        .toList();

                paginator.setPage(page);
                paginator.setResults(premiosDTO);
                paginator.setTotalPages(premios.getTotalPages());
                paginator.setTotalResults((int)premios.getTotalElements());

                return paginator;
        }

        throw new NotFoudException(PREMIO_NOTFOUND);

    }


    @Override
    @Transactional(readOnly = true)
    public List<PremioAnyoDTO> getPremioAnyos() throws NotFoudException {
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
            throw new NotFoudException(PREMIOS_NOTFOUND);
        }
    }
}
