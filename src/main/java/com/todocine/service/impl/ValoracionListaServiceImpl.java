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
import com.todocine.exceptions.ConflictException;
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

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Override
    @Transactional
    public ValoracionListaDTO guardarValoracionLista(Long listaId, ValoracionListaReqDTO valoracionListaReqDTO) {
        ValoracionLista valoracionLista = null;
        Usuario usuario = usuarioDAO.findByUsername(valoracionListaReqDTO.getUsername());

        if (!listaId.equals(valoracionListaReqDTO.getListaId())) {
            throw new BadRequestException(ID_NOT_MATCH);
        }

        if (usuario != null && getCurrentUserId().equals(usuario.getId())) {

            Lista lista = listaDAO.findById(valoracionListaReqDTO.getListaId()).orElse(null);

            if (lista == null) {
                throw new NotFoudException(LISTA_NOT_FOUND);
            }

            ValoracionListaId id = new ValoracionListaId(usuario, lista);
            valoracionLista = valoracionListaDAO.findById(id).orElse(null);

            if (valoracionLista == null) {
                valoracionLista = new ValoracionLista(id);
                valoracionLista.setFecha(LocalDateTime.now());
            }

            valoracionLista.setComentario(valoracionListaReqDTO.getComentario());
            valoracionLista.setPuntuacion(valoracionListaReqDTO.getPuntuacion());

            return ValoracionMapper.toDTO(valoracionListaDAO.save(valoracionLista));

        } else {
            throw new ForbiddenException(USER_FORBIDDEN);
        }
    }

    @Override
    public List<ValoracionListaDTO> getListaValoraciones(Long listaId) {
        Lista lista = listaDAO.findById(listaId).orElseThrow(() -> new NotFoudException(LISTA_NOT_FOUND));

        if (!getCurrentUserId().equals(lista.getUsuario().getId()) && !"S".equals(lista.getPublica()))
            throw new ForbiddenException(USER_FORBIDDEN);

        List<ValoracionLista> valoracionListaList = valoracionListaDAO.findByIdListaId(lista.getId());

        if (valoracionListaList != null)
            return valoracionListaList.stream().map(ValoracionMapper::toDTO).toList();

        return new ArrayList<>();
    }
}
