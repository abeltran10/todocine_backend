package com.todocine.controller;

import com.todocine.dto.MovieDTO;
import com.todocine.dto.MovieDetailDTO;
import com.todocine.exceptions.BadGatewayException;
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

@RestController
@RequestMapping("/movie")
public class MovieController {
    Logger LOG = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;


    @GetMapping("/search")
    public ResponseEntity<Paginator<MovieDTO>> getMovieByName(@NotBlank @RequestParam("name") String name, @RequestParam("page") Integer pagina)
            throws NotFoudException, BadGatewayException {
        Paginator<MovieDTO> paginator = movieService.getMovieByName(name, pagina);
        ResponseEntity<Paginator<MovieDTO>> responseEntity = new ResponseEntity<>(paginator, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<MovieDetailDTO> getMovieDetailById(@NotBlank @PathVariable("id") String id) throws NotFoudException, BadGatewayException {
        ResponseEntity<MovieDetailDTO> responseEntity = new ResponseEntity<>(movieService.getMovieDetailById(id), HttpStatus.OK);
        return responseEntity;

    }

    @GetMapping("/now")
    public ResponseEntity<Paginator<MovieDTO>> getMoviesPlayingNow(@NotBlank @RequestParam("region") String region, @RequestParam("page") Integer pagina)
            throws NotFoudException, BadGatewayException {
        Paginator<MovieDTO> paginator = movieService.getMoviesPlayingNow(region, pagina);
        ResponseEntity<Paginator<MovieDTO>> responseEntity = new ResponseEntity<>(paginator, HttpStatus.OK);
        return responseEntity;
    }


}
