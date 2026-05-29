package com.todocine.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Embeddable
public class ValoracionListaId {

    @ManyToOne
    @JoinColumn(name = "usuario", referencedColumnName = "ID")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "lista", referencedColumnName = "ID")
    private Lista lista;

    public ValoracionListaId() {
    }

    public ValoracionListaId(Usuario usuario, Lista lista) {
        this.usuario = usuario;
        this.lista = lista;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista lista) {
        this.lista = lista;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ValoracionListaId that)) return false;
        return Objects.equals(usuario, that.usuario) && Objects.equals(lista, that.lista);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, lista);
    }
}
