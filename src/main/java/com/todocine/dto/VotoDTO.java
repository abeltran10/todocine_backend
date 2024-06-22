package com.todocine.dto;

import com.todocine.entities.Voto;
import jakarta.validation.constraints.NotBlank;

public class VotoDTO {

    private String id;

    @NotBlank
    private UsuarioDTO usuarioDTO;

    @NotBlank
    private MovieDTO movieDTO;

    @NotBlank
    private Double voto;

    public VotoDTO() {
    }

    public VotoDTO(String id) {
        this.id = id;
    }

    public VotoDTO(String id, UsuarioDTO usuarioDTO, MovieDTO movieDTO, Double voto) {
        this.id = id;
        this.usuarioDTO = usuarioDTO;
        this.movieDTO = movieDTO;
        this.voto = voto;
    }

    public VotoDTO(Voto voto) {
        this.id = voto.getId();
        this.usuarioDTO = new UsuarioDTO(voto.getUsuario().getId());
        this.movieDTO = new MovieDTO(voto.getMovie().getId());
        this.voto = voto.getVoto();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UsuarioDTO getUsuario() {
        return usuarioDTO;
    }

    public void setUsuario(UsuarioDTO usuarioDTO) {
        this.usuarioDTO = usuarioDTO;
    }

    public MovieDTO getMovie() {
        return movieDTO;
    }

    public void setMovie(MovieDTO movieDTO) {
        this.movieDTO = movieDTO;
    }

    public Double getVoto() {
        return voto;
    }

    public void setVoto(Double voto) {
        this.voto = voto;
    }
}
