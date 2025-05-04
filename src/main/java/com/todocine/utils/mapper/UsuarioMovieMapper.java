package com.todocine.utils.mapper;

import com.todocine.dto.UsuarioMovieDTO;
import com.todocine.entities.UsuarioMovie;
import com.todocine.entities.UserMovieId;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioMovieMapper {

    public static UsuarioMovie toEntity(UsuarioMovieDTO usuarioMovieDTO) {
        Usuario usuario = new Usuario(usuarioMovieDTO.getUsuarioId());
        Movie movie = new Movie(usuarioMovieDTO.getMovieId());

        UsuarioMovie usuarioMovie = new UsuarioMovie(new UserMovieId(usuario, movie));
        usuarioMovie.setFavoritos(usuarioMovieDTO.getFavoritos() ? "S" : "N");
        usuarioMovie.setVista(usuarioMovieDTO.getVista() ? "S" : "N");
        usuarioMovie.setVoto(usuarioMovie.getVoto());

        return usuarioMovie;
    }

    public static UsuarioMovieDTO toDTO(UsuarioMovie usuarioMovie) {
        UsuarioMovieDTO usuarioMovieDTO = new UsuarioMovieDTO(usuarioMovie.getId().getUsuario().getId(), usuarioMovie.getId().getMovie().getId());
        usuarioMovieDTO.setFavoritos(usuarioMovie.getFavoritos().equals("S"));
        usuarioMovieDTO.setVista(usuarioMovie.getVista().equals("S"));
        usuarioMovieDTO.setVoto(usuarioMovie.getVoto());

        return usuarioMovieDTO;
    }
}
