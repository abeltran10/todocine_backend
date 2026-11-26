package com.todocine.service;

import com.todocine.entities.Usuario;
import com.todocine.enums.TipoActividad;

public interface ActividadService {

    void guardarActividad(Usuario usuario, TipoActividad tipoActividad, Long referenciaId, String titulo, String posterPath, String detalleExtra);
}
