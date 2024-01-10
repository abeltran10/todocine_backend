package com.todocine.service;

import java.io.IOException;
import java.util.Map;

public interface TMDBService {

    Map<String, Object> getMovieById(String id) throws IOException;

    Map<String, Object> getMoviesByName(String name, Integer pagina) throws IOException;

    Map<String, Object> getMoviesPlayingNow(String country, Integer pagina) throws IOException;

    Map<String, Object> getVideosByMovieId(String movieId) throws IOException;
}
