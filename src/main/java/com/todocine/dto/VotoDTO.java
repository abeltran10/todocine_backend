package com.todocine.dto;

import com.todocine.model.Voto;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Objects;

@Document(collection = "Voto")
public class VotoDTO {

    @Id
    private String id;

    @DocumentReference
    private UsuarioDTO usuario;

    @DocumentReference
    private MovieDTO movie;

    private Double voto;

    @Version
    private Integer version;

    public VotoDTO() {
    }

    public VotoDTO(String id) {
        this.id = id;
    }

    @PersistenceCreator
    public VotoDTO(String id, UsuarioDTO usuario, MovieDTO movie, Double voto) {
        this.id = id;
        this.usuario = usuario;
        this.movie = movie;
        this.voto = voto;
    }

    public VotoDTO(Voto voto) {
        this.id = voto.getId();
        this.usuario = new UsuarioDTO(voto.getUsuario().getId());
        this.movie = new MovieDTO(voto.getMovie().getId());
        this.voto = voto.getVoto();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public MovieDTO getMovie() {
        return movie;
    }

    public void setMovie(MovieDTO movie) {
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
        if (!(o instanceof VotoDTO)) return false;
        VotoDTO votoDTO = (VotoDTO) o;
        return id.equals(votoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
