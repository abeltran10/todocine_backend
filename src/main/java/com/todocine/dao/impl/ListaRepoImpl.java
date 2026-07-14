package com.todocine.dao.impl;

import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todocine.configuration.Constants;
import com.todocine.dao.ListaRepo;
import com.todocine.dto.response.Paginator;
import com.todocine.entities.Movie;
import com.todocine.entities.QLista;
import com.todocine.entities.QMovie;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ListaRepoImpl extends BaseRepoImpl implements ListaRepo {
    private static final QMovie movie = QMovie.movie;
    private static final QLista lista = QLista.lista;

    @Override
    public Paginator<Movie> getMoviesByListaId(Long listaId, String orderBy, String direction, int limit, int pagina) {
        List<Movie> resultList = new ArrayList<>();
        Paginator<Movie> paginator = new Paginator<>();
        long total = 0;
        int offset = (pagina - 1) * limit;

        JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);

        JPAQuery<Movie> q =  queryFactory.select(movie).from(lista).innerJoin(lista.movies, movie).where(lista.id.eq(listaId));
        JPAQuery<Long> count = queryFactory.select(movie.count()).from(lista).innerJoin(lista.movies, movie).where(lista.id.eq(listaId));

        if (Constants.ORDER_ANYO.equalsIgnoreCase(orderBy)) {
            q = "desc".equalsIgnoreCase(direction) ? q.orderBy(movie.releaseDate.desc()) : q.orderBy(movie.releaseDate.asc());
        } else {
            q = "desc".equalsIgnoreCase(direction) ? q.orderBy(movie.title.desc()) : q.orderBy(movie.title.asc());
        }

        resultList = q.limit(limit).offset(offset).fetch();

        total = count.fetchOne();

        paginator.setResults(resultList);
        paginator.setTotalResults((int) total);
        paginator.setTotalPages((int) Math.ceil(total/(double)limit));
        paginator.setPage(pagina);

        return paginator;
    }
}
