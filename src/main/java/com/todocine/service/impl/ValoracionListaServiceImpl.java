package com.todocine.service.impl;

import com.todocine.dao.ListaDAO;
import com.todocine.dao.ValoracionListaDAO;
import com.todocine.dto.request.ValoracionListaDTO;
import com.todocine.dto.response.ListaDTO;
import com.todocine.dto.response.ValoracionDTO;
import com.todocine.entities.Lista;
import com.todocine.entities.Usuario;
import com.todocine.entities.ValoracionLista;
import com.todocine.entities.ValoracionListaId;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.ForbiddenException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.ValoracionListaService;
import com.todocine.utils.mapper.ListaMapper;
import com.todocine.utils.mapper.ValoracionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.todocine.configuration.Constants.*;

@Service
public class ValoracionListaServiceImpl extends BaseServiceImpl implements ValoracionListaService {

    @Autowired
    private ValoracionListaDAO valoracionListaDAO;

    @Autowired
    private ListaDAO listaDAO;

    @Override
    @Transactional
    public ValoracionDTO updateValoracionLista(Long listaId, Long usuarioId, ValoracionListaDTO valoracionListaDTO) {
        ValoracionLista valoracionLista = null;

        if (!listaId.equals(valoracionListaDTO.getListaId()) || !usuarioId.equals(valoracionListaDTO.getUsuarioId())) {
            throw new BadRequestException(ID_NOT_MATCH);
        }

        if (getCurrentUserId().equals(usuarioId)) {

            Lista lista = listaDAO.findById(valoracionListaDTO.getListaId()).orElse(null);

            if (lista == null) {
                throw new NotFoudException(LISTA_NOT_FOUND);
            }

            ValoracionListaId id = new ValoracionListaId(new Usuario(valoracionListaDTO.getUsuarioId()), lista);
            valoracionLista = valoracionListaDAO.findById(id).orElse(null);

            if (valoracionLista == null) {
                if ((valoracionListaDTO.getComentario() != null && !valoracionListaDTO.getComentario().isBlank())
                        || valoracionListaDTO.getPuntuacion() != null) {
                    valoracionLista = new ValoracionLista(id);
                    valoracionLista.setComentario(valoracionListaDTO.getComentario());
                    valoracionLista.setPuntuacion(valoracionLista.getPuntuacion());
                    valoracionLista.setFecha(LocalDateTime.now());
                } else {
                    throw new BadRequestException(VALORATION_ERROR);
                }
            } else {
                if (valoracionListaDTO.getComentario() != null && !valoracionListaDTO.getComentario().isBlank())
                    valoracionLista.setComentario(valoracionListaDTO.getComentario());
                if (valoracionLista.getPuntuacion() == null && valoracionListaDTO.getPuntuacion() != null)
                    valoracionLista.setPuntuacion(valoracionListaDTO.getPuntuacion());
            }

            return ValoracionMapper.toDTO(valoracionListaDAO.save(valoracionLista));

        } else {
            throw new ForbiddenException(USER_FORBIDDEN);
        }
    }

    @Override
    public List<ValoracionDTO> getListaValoraciones(Long listaId) {
        List<ValoracionLista> valoracionListaList = valoracionListaDAO.findByIdListaId(listaId);

        if (valoracionListaList != null)
            return valoracionListaList.stream().map(ValoracionMapper::toDTO).toList();

        return new ArrayList<>();
    }
}
