package com.todocine.dao;

import com.todocine.entities.Premio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PremioDAO extends JpaRepository<Premio, Long> {

    Premio findByCodigo(Integer codigo);

    @Query(value = "SELECT P.ID, P.TITULO, G.ANYO FROM PREMIO P INNER JOIN GANADOR G ON P.ID=G.PREMIO GROUP BY (P.ID, P.TITULO, G.ANYO) ORDER BY G.ANYO ASC", nativeQuery = true)
    List<Object[]> getPremiosAnyo();
}
