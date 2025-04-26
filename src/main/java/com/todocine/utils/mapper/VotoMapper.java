package com.todocine.utils.mapper;

import com.todocine.dto.VotoDTO;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import com.todocine.entities.Voto;
import com.todocine.entities.VotoId;

import java.util.ArrayList;
import java.util.List;

public class VotoMapper {

    public static Voto toEntity(VotoDTO votoDTO) {
        Voto voto = new Voto();
        Usuario usuario = new Usuario(votoDTO.getUsuarioId());
        Movie movie = new Movie(votoDTO.getMovieId());

        voto.setId(new VotoId(usuario, movie));
        voto.setVoto(votoDTO.getVoto());

        return voto;
    }

    public static VotoDTO toDTO(Voto voto) {
        VotoDTO votoDTO = new VotoDTO();

        votoDTO.setUsuarioId(voto.getId().getUsuario().getId());
        votoDTO.setMovieId(voto.getId().getMovie().getId());
        votoDTO.setVoto(voto.getVoto());

        return votoDTO;
    }


    public static List<VotoDTO> toDTOList (List<Voto> votoList) {
        List<VotoDTO> votoDTOList = new ArrayList<>();

        votoDTOList.addAll(votoList.stream()
                .map(VotoMapper::toDTO)
                .toList());

        return votoDTOList;
    }
}
