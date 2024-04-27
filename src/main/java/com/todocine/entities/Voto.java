package com.todocine.entities;

import com.todocine.dto.VotoDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Objects;

@Document(collection = "Voto")
public class Voto {

    @Id
    private String id;

    @DocumentReference
    private Usuario usuario;

    @DocumentReference
    private Movie movie;

    private Double voto;

    @Version
    private Integer version;

    public Voto() {
    }

    public Voto(String id) {
        this.id = id;
    }

    @PersistenceCreator
    public Voto(String id, Usuario usuario, Movie movie, Double voto) {
        this.id = id;
        this.usuario = usuario;
        this.movie = movie;
        this.voto = voto;
    }

    public Voto(VotoDTO votoDTO) {
        this.id = votoDTO.getId();
        this.usuario = new Usuario(votoDTO.getUsuario().getId());
        this.movie = new Movie(votoDTO.getMovie().getId());
        this.voto = votoDTO.getVoto();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Double getVoto() {
        return voto;
    }

    public void setVoto(Double voto) {
        this.voto = voto;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
}
