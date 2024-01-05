package com.todocine.dao;

import java.io.IOException;
import java.util.List;

public interface MovieDAO {

    String getMovieById(String id) throws IOException;

    String getMoviesByName(String name, Integer pagina) throws IOException;

    String getMoviesPlayingNow(String country, Integer pagina) throws IOException;
}
