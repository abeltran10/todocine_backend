package com.todocine.model;

import com.todocine.dto.MovieDTO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.dto.VotoDTO;

public class Voto {

    private String id;

    private UsuarioDTO usuario;

    private MovieDTO movie;

    private Double voto;

    public Voto() {
    }

    public Voto(String id) {
        this.id = id;
    }

    public Voto(String id, UsuarioDTO usuario, MovieDTO movie) {
        this.id = id;
        this.usuario = usuario;
        this.movie = movie;
    }

    public Voto(VotoDTO votoDTO) {
        this.id = votoDTO.getId();
        this.usuario = votoDTO.getUsuario();
        this.movie = votoDTO.getMovie();
        this.voto = votoDTO.getVoto();
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
}
