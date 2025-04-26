package com.todocine.utils.mapper;

import com.todocine.dto.FavoritosDTO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.entities.Favoritos;
import com.todocine.entities.FavoritosId;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        List<Favoritos> favoritosList = FavoritosMapper.toEntityList(usuarioDTO.getFavoritos());
        usuario.setFavoritos(favoritosList);

        usuario.setVotos(new ArrayList<>());

        return usuario;
    }

    public static UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setPassword(usuario.getPassword());

        List<FavoritosDTO> favoritosDTOList = FavoritosMapper.toDTOList(usuario.getFavoritos());
        usuarioDTO.setFavoritos(favoritosDTOList);

        usuarioDTO.setVotos(new ArrayList<>());

        return usuarioDTO;
    }

}
