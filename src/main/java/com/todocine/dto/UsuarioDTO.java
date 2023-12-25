package com.todocine.dto;

import com.todocine.model.Usuario;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Document("Usuario")
public class UsuarioDTO implements UserDetails {

    @Id
    private Long id;

    private String username;

    private String password;

    private Boolean cuentaNoExpirada;

    private Boolean cuentaNoBloqueada;

    private Boolean credencialesNoExpiradas;

    private Boolean habilitado;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UsuarioDTO(Long id, String username, String password, Boolean cuentaNoExpirada,
                   Boolean cuentaNoBloqueada, Boolean credencialesNoExpiradas, Boolean habilitado) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cuentaNoExpirada = cuentaNoExpirada;
        this.cuentaNoBloqueada = cuentaNoBloqueada;
        this.credencialesNoExpiradas = credencialesNoExpiradas;
        this.habilitado = habilitado;
    }

    public UsuarioDTO(Usuario usuario) {
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

    @Override
    public boolean isAccountNonExpired() {
        return this.cuentaNoExpirada;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.cuentaNoBloqueada;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credencialesNoExpiradas;
    }

    @Override
    public boolean isEnabled() {
        return this.habilitado;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList((GrantedAuthority) () -> "USER");
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


}
