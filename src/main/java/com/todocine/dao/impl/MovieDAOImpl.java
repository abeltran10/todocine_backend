package com.todocine.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todocine.dao.MovieDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.exceptions.BadGateWayException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MovieDAOImpl implements MovieDAO {

    Logger logger = LoggerFactory.getLogger(MovieDAOImpl.class);

    @Value("${tmdb.api.token}")
    private String API_TOKEN;
    @Override
    public String getMoviesByName(String name, Integer pagina) throws IOException {
        String body = null;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/search/movie?query=" + name + "&include_adult=false&language=es-ES&page=" + pagina)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + API_TOKEN)
                .build();

        Response response = null;
        response = client.newCall(request).execute();

        body = response.body().string();

        logger.info(body);

        return body;


    }
}
