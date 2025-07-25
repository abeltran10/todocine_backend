package com.todocine.dao.impl;

import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todocine.configuration.Constants;
import com.todocine.dao.UsuarioMovieRepo;
import com.todocine.entities.QUsuarioMovie;
import com.todocine.entities.UsuarioMovie;
import com.todocine.utils.Paginator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UsuarioMovieRepoImpl extends BaseRepoImpl implements UsuarioMovieRepo {
    private static final QUsuarioMovie usuarioMovie = QUsuarioMovie.usuarioMovie;
    @Override
    public Paginator<UsuarioMovie> getUserMoviesByFilter(Long usuarioId, Map<String, String> filters, String orderBy,
                                                         int limit, int offset) {
        List<UsuarioMovie> resultList = new ArrayList<>();
        Paginator<UsuarioMovie> paginator = new Paginator<>();
        JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);

        JPAQuery<UsuarioMovie> q =  queryFactory.selectFrom(usuarioMovie)
                .where(usuarioMovie.id.usuario.id.eq(usuarioId).and(usuarioMovie.favoritos.equalsIgnoreCase("s")));
        JPAQuery<Long> count = queryFactory.select(usuarioMovie.count())
                .from(usuarioMovie)
                .where(usuarioMovie.id.usuario.id.eq(usuarioId).and(usuarioMovie.favoritos.equalsIgnoreCase("s")));

        long total = 0L;

        if (filters.get(Constants.VISTA_FILTER) != null && !"".equals(filters.get(Constants.VISTA_FILTER))) {
            count = count.where(usuarioMovie.vista.equalsIgnoreCase(filters.get(Constants.VISTA_FILTER)));

            q = q.where(usuarioMovie.vista.equalsIgnoreCase(filters.get(Constants.VISTA_FILTER)));
        }

        if ("s".equalsIgnoreCase(filters.get(Constants.VOTADA_FILTER))) {
            count = count.where(usuarioMovie.voto.isNotNull());

            q = q.where(usuarioMovie.voto.isNotNull());
        } else if ("n".equalsIgnoreCase(filters.get(Constants.VOTADA_FILTER))) {
            count = count.where(usuarioMovie.voto.isNull());

            q = q.where(usuarioMovie.voto.isNull());
        }

        if (Constants.ORDER_TITULO.equalsIgnoreCase(orderBy))
            q = q.orderBy(usuarioMovie.id.movie.title.asc());
        else if (Constants.ORDER_ANYO.equalsIgnoreCase(orderBy))
            q = q.orderBy(usuarioMovie.id.movie.releaseDate.asc());

        total = count.fetchOne();
        resultList = q.limit(limit).offset(offset).fetch();

        paginator.setResults(resultList);
        paginator.setTotalResults((int)total);
        paginator.setTotalPages((int) total / (limit + 1) + 1);

        return paginator;
    }
}
