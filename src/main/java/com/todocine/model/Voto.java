package com.todocine.model;

import com.todocine.dto.VotoDTO;

public class Voto {

    private String id;

    private Usuario usuario;

    private Movie movie;

    private Double voto;

    public Voto() {
    }

    public Voto(String id) {
        this.id = id;
    }

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
}
