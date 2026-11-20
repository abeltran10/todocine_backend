package com.todocine.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Embeddable
public class VotoComentarioId {

    @ManyToOne
    @JoinColumn(name = "COMENTARIO", referencedColumnName = "ID")
    private Comentario comentario;

    @ManyToOne
    @JoinColumn(name = "USUARIO", referencedColumnName = "USERNAME")
    private Usuario usuario;

    public VotoComentarioId() {
    }

    public VotoComentarioId(Comentario comentario, Usuario usuario) {
        this.comentario = comentario;
        this.usuario = usuario;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VotoComentarioId that)) return false;
        return Objects.equals(comentario, that.comentario) && Objects.equals(usuario, that.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comentario, usuario);
    }
}
