package com.todocine;


import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.UsuarioDTO;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class TodocineApplicationTests {

	public static Logger LOG = LoggerFactory.getLogger(TodocineApplicationTests.class);

	@Autowired
	private MovieDAO movieDAO;

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Test
	void contextLoads() {
	}

	@Before
	void insterUserToMovies() {
		List<MovieDTO> movieDTOS = movieDAO.findAll();

		movieDTOS.forEach(movieDTO -> {
				movieDTO.setUsuarios(Arrays.asList(new UsuarioDTO("658e27b8373bd467ae8bad1a")));

				movieDTO = movieDAO.save(movieDTO);

				LOG.info(movieDTO.getUsuarios().toString());
		});

	}

	@Test
	void findMovieByUser() {

		List<MovieDTO> foundMovies = movieDAO.findByUserId("658e27b8373bd467ae8bad1a");
		assertTrue(foundMovies != null);
		assertTrue(!foundMovies.isEmpty());

		LOG.info(foundMovies.toString());
	}







}
