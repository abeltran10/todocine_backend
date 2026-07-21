package com.todocine.utils;

import com.todocine.dao.MovieDAO;
import com.todocine.dto.response.MovieDTO;
import com.todocine.entities.Movie;
import com.todocine.service.TMDBService;
import com.todocine.utils.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;


@Service
public class MovieSyncScheduler {

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private TMDBService tmdbService;

    @Autowired
    private Environment environment;

    /**
     * DESENCADENANTE 1: Al desplegar / arrancar la aplicación.
     * Se ejecuta automáticamente cuando la app está lista para recibir peticiones.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void syncOnDeploy() {
        // 2. Comprobamos si el perfil "prod" está entre los perfiles activos
        boolean isProd = Arrays.asList(environment.getActiveProfiles()).contains("prod");

        if (isProd) {
            System.out.println("Iniciando sincronización de películas por despliegue de nueva versión...");
            ejecutarSincronizacionGlobal();
        }
    }

    /**
     * DESENCADENANTE 2: Todos los lunes a las 03:00 AM.
     * "0 0 3 ? * MON" -> Segundos, Minutos, Horas, Día del mes, Mes, Día de la semana.
     */
    @Scheduled(cron = "0 0 3 ? * MON", zone = "Europe/Madrid")
    public void syncEveryMonday() {
        System.out.println("Iniciando sincronización programada de los lunes...");
        ejecutarSincronizacionGlobal();
    }

    @Transactional
    private void ejecutarSincronizacionGlobal() {
        int pageNumber = 0;
        int pageSize = 50; // Batch size
        boolean hasMorePages = true;
        long totalProcesadas = 0;

        System.out.println("Iniciando sincronización por lotes (Tamaño de página: " + pageSize + ")...");

        while (hasMorePages) {
            try {
                Pageable pageable = PageRequest.of(pageNumber, pageSize);
                Page<Movie> moviePage = movieDAO.findAll(pageable);

                // If empty finish loop
                if (moviePage.isEmpty()) {
                    hasMorePages = false;
                    break;
                }

                System.out.println("Procesando lote nº " + (pageNumber + 1) + " con " + moviePage.getNumberOfElements() + " películas...");

                for (Movie movie : moviePage.getContent()) {
                    Long id = movie.getId();

                    try {
                        //Get from TMDB
                        Map<String, Object> movieMap = tmdbService.getMovieById(id);
                        if (movieMap.get("id") != null) {
                            MovieDTO movieDTO = MovieMapper.toDTO(movieMap);

                            movie.setOriginalTitle(movieDTO.getOriginalTitle());
                            movie.setTitle(movieDTO.getTitle());
                            movie.setPosterPath(movieDTO.getPosterPath());
                            movie.setOverview(movieDTO.getOverview());

                            DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate fecha = LocalDate.parse(movieDTO.getReleaseDate(), formateador);
                            movie.setReleaseDate(fecha);

                            movie.setPopularity(movieDTO.getPopularity());
                            movie.setVoteCount(movieDTO.getVoteCount());
                            movie.setVoteAverage(movieDTO.getVoteAverage());
                            movie.setOriginalLanguage(movieDTO.getOriginalLanguage());

                            movieDAO.save(movie);

                            totalProcesadas++;
                        } else {
                            System.err.println("Fallo al recuperar la película con ID " + id + " de TMDB");
                        }

                        // Delay de 100ms to respect rate limit of TMDB API
                        Thread.sleep(100);
                    } catch (Exception e) {
                        System.err.println("Error sincronizando la película con ID " + id + ": " + e.getMessage());
                    }
                }

                //Check if there is a next page
                if (moviePage.hasNext()) {
                    pageNumber++;
                } else {
                    hasMorePages = false; //It was last page, finish loop
                }

            } catch (Exception e) {
                System.err.println("Error crítico al recuperar el lote " + pageNumber + ": " + e.getMessage());
                hasMorePages = false; // Interrupt to avoid infinite loop in case DB error
            }
        }

        System.out.println("Sincronización global finalizada. Total de películas procesadas: " + totalProcesadas);
    }
}
