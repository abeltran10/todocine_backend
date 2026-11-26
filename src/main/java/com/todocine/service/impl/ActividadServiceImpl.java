package com.todocine.service.impl;

import com.todocine.dao.ActividadDAO;
import com.todocine.entities.Actividad;
import com.todocine.entities.Usuario;
import com.todocine.enums.TipoActividad;
import com.todocine.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActividadServiceImpl implements ActividadService {

    @Autowired
    private ActividadDAO actividadDAO;

    @Override
    @Transactional
    public void guardarActividad(Usuario usuario,
                                 TipoActividad tipoActividad,
                                 Long referenciaId,
                                 String titulo,
                                 String posterPath,
                                 String detalleExtra) {

        Actividad actividad = new Actividad();

        actividad.setUsuario(usuario);
        actividad.setTipo(tipoActividad);
        actividad.setReferenciaId(referenciaId);
        actividad.setTituloReferencia(titulo);
        actividad.setPosterPath(posterPath);
        actividad.setDetalleExtra(detalleExtra);

        actividadDAO.save(actividad);
    }
}
