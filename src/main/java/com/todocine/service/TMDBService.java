package com.todocine.service;

import java.io.IOException;

public interface TMDBService {

    String getMovieById(String id) throws IOException;

    String getMoviesByName(String name, Integer pagina) throws IOException;

    String getMoviesPlayingNow(String country, Integer pagina) throws IOException;
}
