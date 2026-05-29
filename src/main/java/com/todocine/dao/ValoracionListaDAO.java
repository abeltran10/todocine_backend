package com.todocine.dao;

import com.todocine.entities.ValoracionLista;
import com.todocine.entities.ValoracionListaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValoracionListaDAO extends JpaRepository<ValoracionLista, ValoracionListaId> {

    List<ValoracionLista> findByIdListaId(Long listaId);

    void deleteByIdListaId(Long listaId);
}
