package com.todocine.service.impl;

import com.todocine.dao.ComentarioDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dto.request.ComentarioReqDTO;
import com.todocine.dto.request.VotoComentarioReqDTO;
import com.todocine.dto.response.ComentarioDTO;
import com.todocine.dto.response.MovieDTO;
import com.todocine.dto.response.VotoComentarioDTO;
import com.todocine.entities.*;
import com.todocine.enums.TipoVoto;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.ForbiddenException;
import com.todocine.exceptions.NotFoudException;
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

    @Override
    @Transactional(readOnly = true)
    public List<ComentarioDTO> getComentarioReplies(Long id) {
        return comentarioDAO.findByParentIdOrderByFechaCreacionDesc(id).stream().map(ComentarioMapper::toDTO).toList();
    }

    @Override
    @Transactional
    public ComentarioDTO insertarComentario(ComentarioReqDTO comentarioReqDTO) {
        if (!getCurrentUsername().equals(comentarioReqDTO.getUsername())) {
            throw new ForbiddenException(Constants.USER_FORBIDDEN);
        }

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
        }

        Comentario comentario = new Comentario();
        comentario.setMovie(movie);
        comentario.setUsuario(new Usuario(getCurrentUsername()));
        comentario.setContenido(comentarioReqDTO.getContenido());
        comentario.setParent(parent);
        comentario.setFechaCreacion(LocalDateTime.now());

        return ComentarioMapper.toDTO(comentarioDAO.save(comentario));
    }

    @Override
    @Transactional
    public ComentarioDTO updateComentario(Long id, ComentarioReqDTO comentarioReqDTO) {
        if (!id.equals(comentarioReqDTO.getId())) {
            throw new BadRequestException(ID_NOT_MATCH);
        }

        if (!getCurrentUsername().equals(comentarioReqDTO.getUsername())) {
            throw new ForbiddenException(Constants.USER_FORBIDDEN);
        }

        Comentario comentario = comentarioDAO.findById(id).orElseThrow(() -> new NotFoudException(COMMENT_NOTEXISTS));
        comentario.setContenido(comentarioReqDTO.getContenido());

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

        VotoComentarioId votoComentarioId = new VotoComentarioId(comentario, new Usuario(getCurrentUsername()));
        VotoComentario votoComentario = new VotoComentario(votoComentarioId);
        votoComentario.setTipo(votoComentarioReqDTO.getVoto().equals(TipoVoto.LIKE.getValue()) ? TipoVoto.LIKE : TipoVoto.DISLIKE);

        comentario.getVotes().remove(votoComentario);
        comentario.getVotes().add(votoComentario);

        comentarioDAO.save(comentario);

        VotoComentarioDTO comentarioDTO = new VotoComentarioDTO(getCurrentUsername(), comentario.getId());
        comentarioDTO.setVoto(votoComentario.getTipo().getValue());

        return comentarioDTO;

    }
}
