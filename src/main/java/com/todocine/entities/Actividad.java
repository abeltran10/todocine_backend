package com.todocine.entities;

import com.todocine.enums.TipoActividad;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(
        name = "ACTIVIDAD",
        indexes = {
                // Índice vital para que la consulta del timeline por usuario y fecha sea instantánea
                @Index(name = "idx_actividad_usuario_fecha", columnList = "usuario, fecha_creacion DESC")
        }
)
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USUARIO", referencedColumnName = "USERNAME")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO", nullable = false, length = 30)
    private TipoActividad tipo;

    private Long referenciaId;

    private String tituloReferencia;

    private String posterPath;

    // Dato extra opcional según el tipo (ej: la nota "9/10" o un extracto corto del comentario)
    @Column(name = "DETALLE_EXTRA", length = 500)
    private String detalleExtra;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    public Actividad() {
    }

    public Actividad(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoActividad getTipo() {
        return tipo;
    }

    public void setTipo(TipoActividad tipo) {
        this.tipo = tipo;
    }

    public Long getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(Long referenciaId) {
        this.referenciaId = referenciaId;
    }

    public String getTituloReferencia() {
        return tituloReferencia;
    }

    public void setTituloReferencia(String tituloReferencia) {
        this.tituloReferencia = tituloReferencia;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getDetalleExtra() {
        return detalleExtra;
    }

    public void setDetalleExtra(String detalleExtra) {
        this.detalleExtra = detalleExtra;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Actividad actividad)) return false;
        return Objects.equals(id, actividad.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
