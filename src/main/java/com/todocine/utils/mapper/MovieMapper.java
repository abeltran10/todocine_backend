package com.todocine.utils.mapper;

import com.todocine.dto.GenreDTO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.VideoDTO;
import com.todocine.dto.VotoDTO;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import com.todocine.entities.Voto;
import com.todocine.entities.VotoId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieMapper {

    public static Movie toEntity(MovieDTO movieDTO) {
        Movie movie = new Movie();

        movie.setId(movieDTO.getId());
        movie.setOriginalTitle(movieDTO.getOriginalTitle());
        movie.setTitle(movieDTO.getTitle());
        movie.setPosterPath(movieDTO.getPosterPath());
        movie.setOverview(movieDTO.getOverview());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setPopularity(movieDTO.getPopularity());
        movie.setVoteCount(movieDTO.getVoteCount());
        movie.setVoteAverage(movieDTO.getVoteAverage());
        movie.setOriginalLanguage(movieDTO.getOriginalLanguage());
        movie.setVotosMediaTC(movieDTO.getVotosMediaTC());
        movie.setTotalVotosTC(movieDTO.getTotalVotosTC());

        return movie;
    }

    public static MovieDTO toDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setId(movie.getId());
        movieDTO.setOriginalTitle(movie.getOriginalTitle());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setPosterPath(movie.getPosterPath());
        movieDTO.setOverview(movie.getOverview());
        movieDTO.setReleaseDate(movie.getReleaseDate());
        movieDTO.setPopularity(movie.getPopularity());
        movieDTO.setVoteCount(movie.getVoteCount());
        movieDTO.setVoteAverage(movie.getVoteAverage());
        movieDTO.setOriginalLanguage(movie.getOriginalLanguage());
        List<VotoDTO> votoDTOS = VotoMapper.toDTOList(movie.getVotosTC());
        movieDTO.setVotos(votoDTOS);
        movieDTO.setVotosMediaTC(movie.getVotosMediaTC());
        movieDTO.setTotalVotosTC(movie.getTotalVotosTC());

        return movieDTO;
    }

    public static List<MovieDTO> toDTOList(List<Movie> movieList) {
        List<MovieDTO> movieDTOList = new ArrayList<>();

        movieDTOList = movieList.stream()
                .map(MovieMapper::toDTO)
                .toList();

        return movieDTOList;
    }

    public static MovieDTO toDTO(Map<String, Object> map) {
        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setId(String.valueOf(map.get("id")));
        movieDTO.setOriginalTitle((String) map.get("original_title"));
        movieDTO.setTitle((String) map.get("title"));
        movieDTO.setPosterPath((String) map.get("poster_path"));
        movieDTO.setOverview((String) map.get("overview"));
        movieDTO.setReleaseDate((String) map.get("release_date"));
        movieDTO.setPopularity((Double) map.get("popularity"));
        movieDTO.setVoteCount((Integer) map.get("vote_count"));
        movieDTO.setVoteAverage((Double) map.get("vote_average"));

        List<GenreDTO> genreDTOList = new ArrayList<>();
        if (map.containsKey("genres")) {
            genreDTOList.addAll(((List<Map<String, Object>>) map.get("genres")).stream()
                    .map(GenreMapper::toDTO)
                    .toList());
            movieDTO.setGenres(genreDTOList);
        } else if (map.containsKey("genre_ids")) {
            genreDTOList.addAll(((List<Integer>) map.get("genre_ids")).stream()
                    .map(genres -> new GenreDTO(String.valueOf(genres)))
                    .toList());
            movieDTO.setGenres(genreDTOList);
        } else
            movieDTO.setGenres(new ArrayList<>());

        movieDTO.setOriginalLanguage((String) map.get("original_language"));

        Map<String, Object> objectMap = null;
        if (map.containsKey("videos") && !(map.get("videos").equals("false")))
            objectMap = (Map<String, Object>) map.get("videos");

        List<VideoDTO> videoDTOList = new ArrayList<>();
        if (objectMap != null) {
            videoDTOList.addAll(((List<Map<String, Object>>) objectMap.get("results")).stream()
                    .map(VideoMapper::toDTO)
                    .toList());
             movieDTO.setVideos(videoDTOList);
        }

        movieDTO.setVotos(new ArrayList<>());
        movieDTO.setTotalVotosTC(0);
        movieDTO.setVotosMediaTC(0D);

        return movieDTO;
    }
}
