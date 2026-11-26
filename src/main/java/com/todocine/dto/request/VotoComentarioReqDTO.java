package com.todocine.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VotoComentarioReqDTO {

    @NotBlank
    private String username;

    @NotNull
    private Long comentarioId;

    @NotNull
    private Integer voto;

    public VotoComentarioReqDTO() {
    }

    public VotoComentarioReqDTO(String username, Long comentarioId) {
        this.username = username;
        this.comentarioId = comentarioId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getComentarioId() {
        return comentarioId;
    }

    public void setComentarioId(Long comentarioId) {
        this.comentarioId = comentarioId;
    }

    public Integer getVoto() {
        return voto;
    }

    public void setVoto(Integer voto) {
        this.voto = voto;
    }
}
