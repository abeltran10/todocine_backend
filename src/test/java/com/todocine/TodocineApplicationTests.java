package com.todocine;


import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.exceptions.BadGateWayException;
import com.todocine.model.MoviePage;
import com.todocine.service.MovieService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class TodocineApplicationTests {

	public static Logger LOG = LoggerFactory.getLogger(TodocineApplicationTests.class);

	@Autowired
	private MovieService movieService;

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Test
	void contextLoads() {
	}


	@Test
	void getMoviePage() throws BadGateWayException {
		MoviePage moviePage = movieService.getMovieByName("Forrest", 1);

		assertTrue (moviePage.getResults().size() == 20);


	}

	@Test
	void getUsuarioByName() {
		List<UsuarioDTO> usuarioDTOS = usuarioDAO.findByUsername("user1234");

		LOG.info(usuarioDTOS.toString());

		assertTrue (usuarioDTOS.get(0).getUsername().equals("user1234"));

	}






}
