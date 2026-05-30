package com.todocine.service.impl;

import com.todocine.dao.ListaDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dao.ValoracionListaDAO;
import com.todocine.dto.request.ListaReqDTO;
import com.todocine.dto.response.ListaDTO;
import com.todocine.dto.response.MovieDTO;
import com.todocine.dto.response.MovieListaDTO;
import com.todocine.dto.response.ValoracionDTO;
import com.todocine.entities.Lista;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import com.todocine.entities.ValoracionLista;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.ForbiddenException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.ListaService;
import com.todocine.service.TMDBService;
import com.todocine.utils.Paginator;
import com.todocine.utils.mapper.ListaMapper;
import com.todocine.utils.mapper.MovieListaMapper;
import com.todocine.utils.mapper.MovieMapper;
import com.todocine.utils.mapper.ValoracionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.todocine.configuration.Constants.*;

@Service
public class ListaServiceImpl extends BaseServiceImpl implements ListaService {

    @Autowired
    private ListaDAO listaDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private ValoracionListaDAO valoracionListaDAO;

    @Autowired
    private TMDBService tmdbService;

    @Override
    public Paginator<ListaDTO> getListasUser(Long usuarioId, Integer page) {
        Paginator<ListaDTO> paginator = new Paginator<>();
        Pageable pageable = PageRequest.of(page - 1, 10);

        if (getCurrentUserId().equals(usuarioId)) {
            Page<Lista> listasPage = listaDAO.findByUsuarioId(usuarioId, pageable);

            if (listasPage.hasContent()) {
                List<ListaDTO> results = listasPage.getContent().stream()
                        .map(ListaMapper::toDTO)
                        .toList();

                paginator.setResults(results);
                paginator.setPage(page);
                paginator.setTotalPages(listasPage.getTotalPages());
                paginator.setTotalResults(Integer.parseInt(String.valueOf(listasPage.getTotalElements())));
            }

            return paginator;
        } else {
            throw new ForbiddenException(USER_FORBIDDEN);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ListaDTO getListaById(Long id) {

        Lista lista = listaDAO.findById(id)
                .orElseThrow(() -> new NotFoudException(LISTA_NOT_FOUND));

        Usuario usuario = lista.getUsuario();

        if ((usuario != null && getCurrentUserId().equals(usuario.getId())) || "S".equals(lista.getPublica())) {

            return ListaMapper.toDTO(lista);

        } else {
            throw new ForbiddenException(USER_FORBIDDEN);
        }


    }

    @Override
    @Transactional
    public ListaDTO createLista(ListaReqDTO listaDTO) {

        Usuario usuario = usuarioDAO.findByUsername(listaDTO.getUsername());

        if (usuario == null || !usuario.getId().equals(getCurrentUserId())) {
            throw new ForbiddenException(USER_FORBIDDEN);
        }

        Lista lista = new Lista();
        lista.setNombre(listaDTO.getNombre());
        lista.setDescripcion(listaDTO.getDescripcion());
        lista.setUsuario(usuario);
        lista.setPublica("N");

        return ListaMapper.toDTO(listaDAO.save(lista));
    }

    @Override
    @Transactional
    public ListaDTO updateLista(Long id, ListaReqDTO listaDTO) {

        Usuario usuario = usuarioDAO.findByUsername(listaDTO.getUsername());

        if (usuario == null || !getCurrentUserId().equals(usuario.getId()))
            throw new ForbiddenException(USER_FORBIDDEN);

        if (!id.equals(listaDTO.getId())) {
            throw new BadRequestException(ID_NOT_MATCH);
        }

        Lista listaExistente = listaDAO.findById(id)
                .orElseThrow(() -> new NotFoudException(LISTA_NOT_FOUND));

        listaExistente.setNombre(listaDTO.getNombre());
        listaExistente.setDescripcion(listaDTO.getDescripcion());
        listaExistente.setPublica(listaDTO.getPublica() != null && listaDTO.getPublica() ? "S" : "N");

        return ListaMapper.toDTO(listaDAO.save(listaExistente));
    }

    @Override
    @Transactional
    public void deleteLista(Long id) {
        Lista lista = listaDAO.findById(id)
                .orElseThrow(() -> new NotFoudException(LISTA_NOT_FOUND));

        if (!lista.getUsuario().getId().equals(getCurrentUserId()))
            throw new ForbiddenException(USER_FORBIDDEN);

        listaDAO.delete(lista);
    }

    @Override
    @Transactional
    public ListaDTO addMovieToList(Long listaId, Long movieId) {

        List<ValoracionDTO> valoracionDTOList = new ArrayList<>();

        Lista lista = listaDAO.findById(listaId)
                .orElseThrow(() -> new NotFoudException(LISTA_NOT_FOUND));

        if (getCurrentUserId().equals(lista.getUsuario().getId())) {
            Movie movie = movieDAO.findById(movieId).orElse(null);

            if (movie == null) {
                try {
                    Map<String, Object> movieMap = tmdbService.getMovieById(movieId);

                    if (movieMap.get("id") == null) {
                        throw new NotFoudException(MOVIE_NOTFOUND);
                    }

                    MovieDTO movieDTO = MovieMapper.toDTO(movieMap);
                    movie = movieDAO.save(MovieMapper.toEntity(movieDTO));

                } catch (IOException e) {
                    throw new BadGatewayException(TMDB_ERROR);
                }
            }

            if (!lista.getMovies().contains(movie)) {
                lista.getMovies().add(movie);
            }

            return ListaMapper.toDTO(listaDAO.save(lista));

        } else {
            throw new ForbiddenException(USER_FORBIDDEN);
        }

    }

    @Override
    @Transactional
    public void deleteMovieFromList(Long listaId, Long movieId) {
        Lista lista = listaDAO.findById(listaId)
                .orElseThrow(() -> new NotFoudException(LISTA_NOT_FOUND));

        if (getCurrentUserId().equals(lista.getUsuario().getId())) {
            boolean removed = lista.getMovies().removeIf(movie -> movie.getId().equals(movieId));

            if (!removed) {
                throw new NotFoudException(MOVIE_NOTFOUND);
            }

            listaDAO.save(lista);
        } else {
            throw new ForbiddenException(USER_FORBIDDEN);
        }
    }

    @Override
    public Paginator<ListaDTO> getListasPublicas(Integer page) {
        Paginator<ListaDTO> paginator = new Paginator<>();

        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Lista> listasPage = listaDAO.findByPublica("S", pageable);

        if (listasPage.hasContent()) {
            List<ListaDTO> results = listasPage.getContent().stream()
                    .map(ListaMapper::toDTO)
                    .toList();

            paginator.setResults(results);
            paginator.setPage(page);
            paginator.setTotalPages(listasPage.getTotalPages());
            paginator.setTotalResults(Integer.parseInt(String.valueOf(listasPage.getTotalElements())));
        }

       return paginator;
    }

    @Override
    @Transactional(readOnly = true)
    public Paginator<MovieListaDTO> getMoviesByLista(Long listaId, Integer pagina) {
        Paginator<MovieListaDTO> paginator = new Paginator<>();
        int skip = (pagina - 1) * 10;

        Lista lista = listaDAO.findById(listaId).orElseThrow(() -> new NotFoudException(LISTA_NOT_FOUND));

        if (getCurrentUserId().equals(lista.getUsuario().getId()) || "S".equals(lista.getPublica())) {
            List<MovieListaDTO> movieListaDTOS = lista.getMovies().stream()
                    .map(MovieListaMapper::toDTO)
                    .skip(skip)
                    .limit(10)
                    .toList();


            if (movieListaDTOS.isEmpty())
                return paginator;

            int total = lista.getMovies().size();

            paginator.setPage(pagina);
            paginator.setResults(movieListaDTOS);
            paginator.setTotalResults(total);
            paginator.setTotalPages((int) Math.ceil(total/10D));

            return paginator;
        } else {
            throw new ForbiddenException(USER_FORBIDDEN);
        }
    }
}
