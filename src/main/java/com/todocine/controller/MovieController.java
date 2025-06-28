package com.todocine.controller;

import com.todocine.configuration.Constants;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.MovieDetailDTO;
import com.todocine.exceptions.BadGatewayException;
import com.todocine.exceptions.BadRequestException;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.MovieService;
import com.todocine.utils.Paginator;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/movies")
public class MovieController {
    Logger LOG = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;


    @GetMapping
    public ResponseEntity<Paginator<MovieDTO>> getMovies(@RequestParam("name") String name, @RequestParam("status") String status,
                                                         @RequestParam("region") String region, @RequestParam("page") Integer pagina)
            throws NotFoudException, BadRequestException, BadGatewayException {

        Map<String, String> filters = new HashMap<>();
        filters.put(Constants.MOVIE_NAME, name);
        filters.put(Constants.MOVIE_STATUS, status);
        filters.put(Constants.MOVIE_REGION, region);

        Paginator<MovieDTO> paginator = movieService.getMovies(filters, pagina);
        ResponseEntity<Paginator<MovieDTO>> responseEntity = new ResponseEntity<>(paginator, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailDTO> getMovieDetailById(@NotBlank @PathVariable("id") String id) throws NotFoudException, BadGatewayException {
        ResponseEntity<MovieDetailDTO> responseEntity = new ResponseEntity<>(movieService.getMovieDetailById(id), HttpStatus.OK);
        return responseEntity;

    }


}
