package com.todocine;


import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.model.Movie;
import com.todocine.model.MoviePage;
import com.todocine.service.MovieService;
import com.todocine.service.TMDBService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class TodocineApplicationTests {

	public static Logger LOG = LoggerFactory.getLogger(TodocineApplicationTests.class);

	@Autowired
	private MovieService movieService;

	@Autowired
	private TMDBService tmdbService;

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Test
	void contextLoads() {
	}


	@Test
	void getMoviePage() {
		MoviePage moviePage = movieService.getMovieByName("Forrest", 1);

		assertTrue (moviePage.getResults().size() == 20);


	}

	@Test
	void getUsuarioByName() {
		UsuarioDTO usuarioDTO = usuarioDAO.findByUsername("user1234");

		LOG.info(usuarioDTO.toString());

		assertTrue (usuarioDTO.getUsername().equals("user1234"));

	}

	@Test
	void getMovieById() {
		Movie movie = null;

		try {
			movie = movieService.getMovieById("13");
		}  catch (Exception ex ) {
			throw new RuntimeException(ex);
		} finally {
			LOG.info(movie.toString());
			assertTrue(movie.getVideos() != null);
		}
	}

	@Test
	void getMoviesPlayingNow() {
		MoviePage movies = null;

		try {
			movies = movieService.getMoviesPlayingNow("ES", 1);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			LOG.info(movies.toString());
			assertTrue(movies != null && !movies.getResults().isEmpty());
		}
	}






}
