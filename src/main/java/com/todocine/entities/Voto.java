package com.todocine.entities;

import com.todocine.dto.VotoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "VOTO")
public class Voto {

    @EmbeddedId
    private VotoId id;

    @Column
    private Double voto;

    public Voto() {
    }

    public Voto(VotoId id) {
        this.id = id;
    }

    public Voto(VotoId id, Double voto) {
        this.id = id;
        this.voto = voto;
    }

    public VotoId getId() {
        return id;
    }

    public void setId(VotoId id) {
        this.id = id;
    }

    public Double getVoto() {
        return voto;
    }

    public void setVoto(Double voto) {
        this.voto = voto;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Voto)) return false;
        Voto voto = (Voto) o;
        return id.equals(voto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Voto{" +
                "id=" + id +
                ", voto=" + voto +
                '}';
    }
}
