package com.todocine.dao;

import com.todocine.entities.ValoracionLista;
import com.todocine.entities.ValoracionListaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ValoracionListaDAO extends JpaRepository<ValoracionLista, ValoracionListaId> {

    @Query(value = "SELECT * FROM VALORACIONLISTA WHERE lista = :listaId ORDER BY FECHA DESC", nativeQuery = true)
    List<ValoracionLista> findByIdListaId(@Param("listaId") Long listaId);

    void deleteByIdListaId(Long listaId);
}
