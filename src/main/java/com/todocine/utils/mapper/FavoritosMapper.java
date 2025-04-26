package com.todocine.utils.mapper;

import com.todocine.dto.FavoritosDTO;
import com.todocine.entities.Favoritos;
import com.todocine.entities.FavoritosId;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;

import java.util.ArrayList;
import java.util.List;

public class FavoritosMapper {

    public static Favoritos toEntity(FavoritosDTO favoritosDTO) {
        Usuario usuario = new Usuario(favoritosDTO.getUsuarioId());
        Movie movie = new Movie(favoritosDTO.getMovieId());

        Favoritos favoritos = new Favoritos(new FavoritosId(usuario, movie));

        return favoritos;
    }

    public static FavoritosDTO toDTO(Favoritos favoritos) {
        FavoritosDTO favoritosDTO = new FavoritosDTO(favoritos.getId().getUsuario().getId(), favoritos.getId().getMovie().getId());

        return favoritosDTO;
    }

    public static List<FavoritosDTO> toDTOList(List<Favoritos> favoritos) {
        List<FavoritosDTO> favoritosDTOS = new ArrayList<>();

        favoritosDTOS.addAll(favoritos.stream()
                .map(FavoritosMapper::toDTO)
                .toList());

        return favoritosDTOS;
    }

    public static List<Favoritos> toEntityList(List<FavoritosDTO> favoritosDTOList) {
        List<Favoritos> favoritosList = new ArrayList<>();

        favoritosList.addAll(favoritosDTOList.stream()
                .map(FavoritosMapper::toEntity)
                .toList());

        return favoritosList;
    }
}
