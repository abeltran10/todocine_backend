package com.todocine.model;

import com.todocine.dto.UsuarioDTO;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

public class Usuario {

    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
    private Boolean cuentaNoExpirada;

    private Boolean cuentaNoBloqueada;

    private Boolean credencialesNoExpiradas;

    private Boolean habilitado;

    public Usuario() {
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Usuario(Long id, String username, String password, Boolean cuentaNoExpirada,
                   Boolean cuentaNoBloqueada, Boolean credencialesNoExpiradas, Boolean habilitado) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cuentaNoExpirada = cuentaNoExpirada;
        this.cuentaNoBloqueada = cuentaNoBloqueada;
        this.credencialesNoExpiradas = credencialesNoExpiradas;
        this.habilitado = habilitado;
    }

    public Usuario(UsuarioDTO usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.password = usuario.getPassword();
        this.cuentaNoExpirada = usuario.getCuentaNoExpirada();
        this.cuentaNoBloqueada = usuario.getCuentaNoBloqueada();
        this.credencialesNoExpiradas = usuario.getCredencialesNoExpiradas();
        this.habilitado = usuario.getHabilitado();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getCuentaNoExpirada() {
        return cuentaNoExpirada;
    }

    public void setCuentaNoExpirada(Boolean cuentaNoExpirada) {
        this.cuentaNoExpirada = cuentaNoExpirada;
    }

    public Boolean getCuentaNoBloqueada() {
        return cuentaNoBloqueada;
    }

    public void setCuentaNoBloqueada(Boolean cuentaNoBloqueada) {
        this.cuentaNoBloqueada = cuentaNoBloqueada;
    }

    public Boolean getCredencialesNoExpiradas() {
        return credencialesNoExpiradas;
    }

    public void setCredencialesNoExpiradas(Boolean credencialesNoExpiradas) {
        this.credencialesNoExpiradas = credencialesNoExpiradas;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList((GrantedAuthority) () -> "USER");
    }

}
