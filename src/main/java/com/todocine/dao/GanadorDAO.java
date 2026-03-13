package com.todocine.dao;

import com.todocine.entities.Ganador;
import com.todocine.entities.GanadorId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GanadorDAO extends JpaRepository<Ganador, GanadorId> {

    Page<Ganador> findById_CategoriaPremio_Id_Premio_IdAndId_Anyo(Long premio, Integer anyo, Pageable pageable);

    @Query("SELECT DISTINCT g.id.anyo FROM Ganador g " +
            "WHERE g.id.categoriaPremio.id.premio.id = :premioId " +
            "ORDER BY g.id.anyo ASC")
    List<Integer> findAnyosByPremioId(Long premioId);
}
