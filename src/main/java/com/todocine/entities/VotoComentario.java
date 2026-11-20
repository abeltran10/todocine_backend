package com.todocine.entities;

import com.todocine.enums.TipoVoto;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
        name = "VOTOCOMENTARIO",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"comentario", "usuario"}) // Un usuario solo vota 1 vez por comentario
        }
)
public class VotoComentario {

    @EmbeddedId
    private VotoComentarioId id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVoto tipo;

    public VotoComentario() {
    }

    public VotoComentario(VotoComentarioId id) {
        this.id = id;
    }

    public VotoComentarioId getId() {
        return id;
    }

    public void setId(VotoComentarioId id) {
        this.id = id;
    }

    public TipoVoto getTipo() {
        return tipo;
    }

    public void setTipo(TipoVoto tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VotoComentario that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}


