package com.todocine.service.impl;

import com.todocine.service.TMDBService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TMDBServiceImpl implements TMDBService {

    Logger logger = LoggerFactory.getLogger(TMDBServiceImpl.class);

    @Value("${tmdb.api.token}")
    private String API_TOKEN;


    @Override
    public String getMovieById(String id) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + id + "?language=es-ES&append_to_response=videos")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + API_TOKEN)
                .build();

        Response response = client.newCall(request).execute();

        String body = response.body().string();

        logger.info(body);

        return body;

    }


    @Override
    public String getMoviesByName(String name, Integer pagina) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/search/movie?query=" + name + "&include_adult=false&language=es-ES&page=" + pagina)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + API_TOKEN)
                .build();

        Response response = client.newCall(request).execute();

        String body = response.body().string();

        logger.info(body);

        return body;

    }

    @Override
    public String getMoviesPlayingNow(String country, Integer pagina) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/now_playing?language=es-ES&region=" + country + "&page=" + pagina)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + API_TOKEN)
                .build();

        Response response = client.newCall(request).execute();

        String body = response.body().string();

        logger.info(body);

        return body;

    }
}
