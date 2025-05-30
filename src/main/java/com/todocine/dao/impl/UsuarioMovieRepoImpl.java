package com.todocine.dao.impl;

import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todocine.dao.UsuarioMovieRepo;
import com.todocine.entities.QUsuarioMovie;
import com.todocine.entities.UsuarioMovie;
import com.todocine.utils.Paginator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioMovieRepoImpl extends BaseRepoImpl implements UsuarioMovieRepo {
    private static final QUsuarioMovie usuarioMovie = QUsuarioMovie.usuarioMovie;
    @Override
    public Paginator<UsuarioMovie> getUserMoviesByFilter(Long usuarioId, String vista, int limit, int offset) {
        List<UsuarioMovie> resultList = new ArrayList<>();
        Paginator<UsuarioMovie> paginator = new Paginator<>();
        JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
        JPAQuery<UsuarioMovie> query = null;
        JPAQuery<UsuarioMovie> q =  queryFactory.selectFrom(usuarioMovie)
                .where(usuarioMovie.id.usuario.id.eq(usuarioId).and(usuarioMovie.favoritos.equalsIgnoreCase("s")));

        long total = 0L;
        if (vista != null) {
            total = queryFactory.select(usuarioMovie.count())
                    .from(usuarioMovie)
                    .where(usuarioMovie.id.usuario.id.eq(usuarioId).and(usuarioMovie.favoritos.equalsIgnoreCase("s"))
                            .and(usuarioMovie.vista.equalsIgnoreCase(vista))).fetchOne();

            resultList = q.where(usuarioMovie.vista.equalsIgnoreCase(vista)).limit(limit).offset(offset).fetch();
        }

        paginator.setResults(resultList);
        paginator.setTotalResults((int)total);
        paginator.setTotalPages((int) total / (limit + 1) + 1);

        return paginator;
    }
}
