package com.todocine.service.impl;

import com.todocine.dao.ComentarioDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dto.request.ComentarioReqDTO;
import com.todocine.dto.response.ComentarioDTO;
import com.todocine.dto.response.MovieDTO;
import com.todocine.entities.Comentario;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import com.todocine.exceptions.BadGatewayException;
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

import static com.todocine.utils.Constants.PARENT_COMMENT_NOTEXISTS;

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
        return comentarioDAO.findByParentId(id).stream().map(ComentarioMapper::toDTO).toList();
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
}
