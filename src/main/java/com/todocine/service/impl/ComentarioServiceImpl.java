package com.todocine.service.impl;

import com.todocine.dao.ComentarioDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dto.request.ComentarioReqDTO;
import com.todocine.dto.request.VotoComentarioReqDTO;
import com.todocine.dto.response.ComentarioDTO;
import com.todocine.dto.response.MovieDTO;
import com.todocine.dto.response.VotoComentarioDTO;
import com.todocine.entities.*;
import com.todocine.enums.TipoActividad;
import com.todocine.enums.TipoVoto;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.ForbiddenException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.ActividadService;
import com.todocine.service.ComentarioService;
import com.todocine.service.MovieService;
import com.todocine.service.TMDBService;
import com.todocine.utils.Constants;
import com.todocine.utils.mapper.ComentarioMapper;
import com.todocine.utils.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static com.todocine.utils.Constants.*;

@Service
public class ComentarioServiceImpl extends BaseServiceImpl implements ComentarioService {

    @Autowired
    private ComentarioDAO comentarioDAO;

    @Autowired
    private TMDBService tmdbService;

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private ActividadService actividadService;

    @Override
    @Transactional(readOnly = true)
    public List<ComentarioDTO> getComentarioReplies(Long id) {
        return comentarioDAO.findByParentIdOrderByFechaCreacionDesc(id).stream().map(ComentarioMapper::toDTO).toList();
    }

    @Override
    @Transactional
    public ComentarioDTO guardarComentario(Long movieId, ComentarioReqDTO comentarioReqDTO) {
        ComentarioDTO comentarioDTO = null;

        if (!movieId.equals(comentarioReqDTO.getMovieId())) {
            throw new BadRequestException(ID_NOT_MATCH);
        }

        if (!getCurrentUsername().equals(comentarioReqDTO.getUsername())) {
            throw new ForbiddenException(Constants.USER_FORBIDDEN);
        }

        if (comentarioReqDTO.getId() == null) {
            comentarioDTO = insertarComentario(comentarioReqDTO);
        } else {
            comentarioDTO = updateComentario(comentarioReqDTO);
        }

        return comentarioDTO;
    }


    private ComentarioDTO insertarComentario(ComentarioReqDTO comentarioReqDTO) {
        Movie movie = movieDAO.findById(comentarioReqDTO.getMovieId()).orElse(null);

        if (movie == null) {
            try {
                MovieDTO movieDTO = tmdbService.getMovieById(comentarioReqDTO.getMovieId());

                if (movieDTO == null) {
                    throw new NotFoudException(Constants.MOVIE_NOTFOUND);
                }

                movie = movieDAO.save(MovieMapper.toEntity(movieDTO));
            } catch (IOException ex) {
                throw new BadGatewayException(Constants.TMDB_ERROR);
            }
        }

        Comentario parent = null;

        if (comentarioReqDTO.getParentId() != null) {
            parent = comentarioDAO.findById(comentarioReqDTO.getParentId())
                    .orElseThrow(() -> new NotFoudException(PARENT_COMMENT_NOTEXISTS));

            if (!parent.getMovie().getId().equals(comentarioReqDTO.getMovieId())) {
                throw new BadRequestException(COMMENT_WRONG_THREAD);
            }

        }

        Comentario comentario = new Comentario();
        comentario.setMovie(movie);
        comentario.setUsuario(new Usuario(getCurrentUsername()));
        comentario.setContenido(comentarioReqDTO.getContenido());
        comentario.setParent(parent);
        comentario.setFechaCreacion(LocalDateTime.now());

        comentarioDAO.save(comentario);

        actividadService.guardarActividad(new Usuario(getCurrentUsername()), TipoActividad.POST_COMENTARIO,
                comentario.getId(), movie.getTitle(),
                movie.getPosterPath(), "Comentario en la película " + movie.getId().toString());

        return ComentarioMapper.toDTO(comentario);
    }

    private ComentarioDTO updateComentario(ComentarioReqDTO comentarioReqDTO) {
        Comentario comentario = comentarioDAO.findById(comentarioReqDTO.getId()).orElseThrow(() -> new NotFoudException(COMMENT_NOTEXISTS));

        if (!getCurrentUsername().equals(comentario.getUsuario().getUsername()))
            throw new ForbiddenException(Constants.USER_FORBIDDEN);

        if (!comentario.getMovie().getId().equals(comentarioReqDTO.getMovieId()))
            throw new BadRequestException(COMMENT_WRONG_MOVIE);

        comentario.setContenido(comentarioReqDTO.getContenido());

        actividadService.guardarActividad(new Usuario(getCurrentUsername()), TipoActividad.UPDATE_COMENTARIO,
                comentario.getId(), comentario.getMovie().getTitle(),
                comentario.getMovie().getPosterPath(),
                "Modificado el comentario para la película " + comentario.getMovie().getId().toString());

        return ComentarioMapper.toDTO(comentarioDAO.save(comentario));

    }

    @Override
    @Transactional
    public VotoComentarioDTO votarComentario(Long id, VotoComentarioReqDTO votoComentarioReqDTO) {
        if (!id.equals(votoComentarioReqDTO.getComentarioId())) {
            throw new BadRequestException(ID_NOT_MATCH);
        }

        if (!getCurrentUsername().equals(votoComentarioReqDTO.getUsername())) {
            throw new ForbiddenException(Constants.USER_FORBIDDEN);
        }

        Comentario comentario = comentarioDAO.findById(votoComentarioReqDTO.getComentarioId())
                .orElseThrow(() -> new NotFoudException(COMMENT_NOTEXISTS));

        TipoVoto nuevoTipo = TipoVoto.LIKE.getValue() == votoComentarioReqDTO.getVoto()
                ? TipoVoto.LIKE
                : TipoVoto.DISLIKE;

        VotoComentario votoExistente = comentario.getVotes().stream()
                .filter(v -> v.getId().getUsuario() != null && getCurrentUsername().equals(v.getId().getUsuario().getUsername()))
                .findFirst()
                .orElse(null);

        if (votoExistente != null) {
            votoExistente.setTipo(nuevoTipo);
        } else {
            VotoComentarioId votoComentarioId = new VotoComentarioId(comentario, new Usuario(getCurrentUsername()));
            VotoComentario nuevoVoto = new VotoComentario(votoComentarioId);
            nuevoVoto.setTipo(nuevoTipo);
            comentario.getVotes().add(nuevoVoto);
        }

        comentarioDAO.save(comentario);

        actividadService.guardarActividad(new Usuario(getCurrentUsername()), TipoActividad.VOTAR_COMENTARIO,
                comentario.getId(), comentario.getMovie().getTitle(),
                comentario.getMovie().getPosterPath(), "Voto para el comentario " + nuevoTipo.getName());

        VotoComentarioDTO votoComentarioDTO = new VotoComentarioDTO(getCurrentUsername(), comentario.getId());
        votoComentarioDTO.setVoto(nuevoTipo.getValue());

        return votoComentarioDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComentarioDTO> getComentariosByMovie(Long id) {
        return comentarioDAO.findByMovieIdAndParentIsNullOrderByFechaCreacionDesc(id)
                .stream().map(ComentarioMapper::toDTO).toList();
    }
}
