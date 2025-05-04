package com.todocine.utils.mapper;

import com.todocine.dto.UsuarioDTO;
import com.todocine.entities.Usuario;

public class UserMapper {

    public static Usuario toEntity(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();

        usuario.setId(usuarioDTO.getId());
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setAccountNonExpired(usuarioDTO.getAccountNonExpired());
        usuario.setAccountNonLocked(usuarioDTO.getAccountNonLocked());
        usuario.setCredentialsNonExpired(usuarioDTO.getCredentialsNonExpired());
        usuario.setEnabled(usuarioDTO.getEnabled());

        return usuario;
    }

    public static UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setPassword(usuario.getPassword());

        return usuarioDTO;
    }

}
