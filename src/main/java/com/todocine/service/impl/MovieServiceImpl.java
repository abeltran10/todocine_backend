package com.todocine.service.impl;

import com.todocine.service.MovieService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MovieServiceImpl implements MovieService {

    Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Value("${tmdb.api.token}")
    private String API_TOKEN;

   public ResponseEntity getMovieByName(String name) {

        ResponseEntity entity = null;

        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/search/movie?query=" + name + "&include_adult=false&language=en-US&page=1")
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer " + API_TOKEN)
                    .build();

            Response response = client.newCall(request).execute();

            String body = response.body().string();

            entity = new ResponseEntity<>(body, HttpStatus.OK);

            logger.info(entity.getBody().toString());

        } catch (IOException e) {
            entity =  new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
            throw new RuntimeException(e);
        } finally {
            return entity;
        }
    }

}
