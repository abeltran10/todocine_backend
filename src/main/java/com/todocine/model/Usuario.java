package com.todocine.model;

import com.todocine.dto.UsuarioDTO;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Usuario {

    private String id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    private Boolean enabled;

    private List<Movie> favoritos;

    private List<Voto> votos;

    public Usuario() {
    }

    public Usuario(String id) {
        this.id = id;
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.favoritos = new ArrayList<>();
    }

    public Usuario(String id, String username, String password, Boolean accountNonExpired,
                   Boolean accountNonLocked, Boolean credentialsNonExpired, Boolean enabled, List<Movie> favoritos,
                   List<Voto> votos) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.favoritos = favoritos;
        this.votos = votos;
    }

    public Usuario(UsuarioDTO usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.password = usuario.getPassword();
        this.accountNonExpired = usuario.isAccountNonExpired();
        this.accountNonLocked = usuario.isAccountNonLocked();
        this.credentialsNonExpired = usuario.isCredentialsNonExpired();
        this.enabled = usuario.isEnabled();
        this.favoritos = usuario.getFavoritos().stream().map(movieDTO -> new Movie(movieDTO.getId())).collect(Collectors.toList());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }
    public boolean isEnabled() {
        return this.enabled;
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

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Movie> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Movie> favoritos) {
        this.favoritos = favoritos;
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                ", favoritos=" + favoritos +
                '}';
    }
}
