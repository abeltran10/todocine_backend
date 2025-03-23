package com.todocine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.todocine.entities.Ganador;


@JsonIgnoreProperties(ignoreUnknown = true)
public class GanadorDTO {

    private String premio;

    private String categoria;

    private Integer anyo;

    private MovieDTO movie;

    public GanadorDTO() {
    }

    public GanadorDTO(Ganador ganador) {
        this.premio = ganador.getId().getPremio().getTitulo();
        this.categoria = ganador.getId().getCategoria().getNombre();
        this.anyo = ganador.getId().getAnyo();
        this.movie = new MovieDTO(ganador.getId().getMovie());
    }

    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getAnyo() {
        return anyo;
    }

    public void setAnyo(Integer anyo) {
        this.anyo = anyo;
    }

    public MovieDTO getMovie() {
        return movie;
    }

    public void setMovie(MovieDTO movie) {
        this.movie = movie;
    }
}
