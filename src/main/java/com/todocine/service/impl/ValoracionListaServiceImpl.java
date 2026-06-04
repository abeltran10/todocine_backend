package com.todocine.service.impl;

import com.todocine.dao.ListaDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dao.ValoracionListaDAO;
import com.todocine.dto.request.ValoracionListaReqDTO;
import com.todocine.dto.response.ValoracionListaDTO;
import com.todocine.entities.Lista;
import com.todocine.entities.Usuario;
import com.todocine.entities.ValoracionLista;
import com.todocine.entities.ValoracionListaId;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.ForbiddenException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.ValoracionListaService;
import com.todocine.utils.mapper.ValoracionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.todocine.configuration.Constants.*;

@Service
public class ValoracionListaServiceImpl extends BaseServiceImpl implements ValoracionListaService {

    @Autowired
    private ValoracionListaDAO valoracionListaDAO;

    @Autowired
    private ListaDAO listaDAO;

    @Override
    @Transactional
    public ValoracionListaDTO updateValoracionLista(Long listaId, ValoracionListaReqDTO valoracionListaReqDTO) {
        ValoracionLista valoracionLista = null;

        if (!listaId.equals(valoracionListaReqDTO.getListaId())) {
            throw new BadRequestException(ID_NOT_MATCH);
        }

        if (getCurrentUserId().equals(valoracionListaReqDTO.getUsuarioId())) {

            Lista lista = listaDAO.findById(valoracionListaReqDTO.getListaId()).orElse(null);

            if (lista == null) {
                throw new NotFoudException(LISTA_NOT_FOUND);
            }

            ValoracionListaId id = new ValoracionListaId(new Usuario(getCurrentUserId()), lista);
            valoracionLista = valoracionListaDAO.findById(id).orElse(null);

            if (valoracionLista == null) {
                if ((valoracionListaReqDTO.getComentario() != null && !valoracionListaReqDTO.getComentario().isBlank())
                        || valoracionListaReqDTO.getPuntuacion() != null) {
                    valoracionLista = new ValoracionLista(id);
                    valoracionLista.setComentario(valoracionListaReqDTO.getComentario());
                    valoracionLista.setPuntuacion(valoracionLista.getPuntuacion());
                    valoracionLista.setFecha(LocalDateTime.now());
                } else {
                    throw new BadRequestException(VALORATION_ERROR);
                }
            } else {
                if (valoracionListaReqDTO.getComentario() != null && !valoracionListaReqDTO.getComentario().isBlank())
                    valoracionLista.setComentario(valoracionListaReqDTO.getComentario());
                if (valoracionLista.getPuntuacion() == null && valoracionListaReqDTO.getPuntuacion() != null && !valoracionListaReqDTO.getPuntuacion().equals(0.0D))
                    valoracionLista.setPuntuacion(valoracionListaReqDTO.getPuntuacion());
            }

            return ValoracionMapper.toDTO(valoracionListaDAO.save(valoracionLista));

        } else {
            throw new ForbiddenException(USER_FORBIDDEN);
        }
    }

    @Override
    public List<ValoracionListaDTO> getListaValoraciones(Long listaId) {
        Lista lista = listaDAO.findById(listaId).orElseThrow(() -> new NotFoudException(LISTA_NOT_FOUND));

        if (!getCurrentUserId().equals(lista.getUsuario().getId()) || !"S".equals(lista.getPublica()))
            throw new ForbiddenException(USER_FORBIDDEN);

        List<ValoracionLista> valoracionListaList = valoracionListaDAO.findByIdListaId(lista.getId());

        if (valoracionListaList != null)
            return valoracionListaList.stream().map(ValoracionMapper::toDTO).toList();

        return new ArrayList<>();
    }
}
