package com.todocine.utils;

import com.todocine.dao.MovieDAO;
import com.todocine.dto.response.MovieDTO;
import com.todocine.entities.Movie;
import com.todocine.exceptions.NotFoudException;
import com.todocine.service.TMDBService;
import com.todocine.utils.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.todocine.configuration.Constants.MOVIE_NOTFOUND;

@Service
public class MovieSyncScheduler {

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private TMDBService tmdbService;

    /**
     * DESENCADENANTE 1: Al desplegar / arrancar la aplicación.
     * Se ejecuta automáticamente cuando la app está lista para recibir peticiones.
     */
    @EventListener(ApplicationReadyEvent.class)
    @Profile("prod")
    public void syncOnDeploy() {
        System.out.println("Iniciando sincronización de películas por despliegue de nueva versión...");
        ejecutarSincronizacionGlobal();
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
        int pageSize = 21; // Tamaño del lote: procesamos de 21 en 21 registros
        boolean hasMorePages = true;
        long totalProcesadas = 0;

        System.out.println("Iniciando sincronización por lotes (Tamaño de página: " + pageSize + ")...");

        while (hasMorePages) {
            try {
                // 1. Pedimos la página actual de IDs a la base de datos
                Pageable pageable = PageRequest.of(pageNumber, pageSize);
                Page<Long> movieIdsPage = movieDAO.findAllIds(pageable);

                // Si la página está vacía, terminamos el bucle
                if (movieIdsPage.isEmpty()) {
                    hasMorePages = false;
                    break;
                }

                System.out.println("Procesando lote nº " + (pageNumber + 1) + " con " + movieIdsPage.getNumberOfElements() + " películas...");

                // 2. Iteramos sobre los IDs de la página actual
                for (Long id : movieIdsPage.getContent()) {
                    try {
                        // Invocamos tu servicio que conecta con TMDB y actualiza la BD local
                        Map<String, Object> movieMap = tmdbService.getMovieById(id);
                        if (movieMap.get("id") != null) {
                            MovieDTO movieDTO = MovieMapper.toDTO(movieMap);
                            Movie movie = movieDAO.findById(id).orElse(null);

                            if (movie == null) {
                                System.err.println("Fallo al recuperar la película " + id + " de la base de datos");
                            } else {
                                movieDTO.setTotalVotosTC(movie.getTotalVotosTC());
                                movieDTO.setVotosMediaTC(movie.getVotosMediaTC());

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
                                movie.setVotosMediaTC(movieDTO.getVotosMediaTC());
                                movie.setTotalVotosTC(movieDTO.getTotalVotosTC());

                                movieDAO.save(movie);

                                totalProcesadas++;
                            }

                        } else {
                            throw new NotFoudException(MOVIE_NOTFOUND);
                        }

                        // Delay de 100ms para respetar el rate limit de la API de TMDB
                        Thread.sleep(100);
                    } catch (Exception e) {
                        System.err.println("Error sincronizando la película con ID " + id + ": " + e.getMessage());
                    }
                }

                // 3. Verificamos si existe una página siguiente
                if (movieIdsPage.hasNext()) {
                    pageNumber++; // Avanzamos a la siguiente página
                } else {
                    hasMorePages = false; // Era la última página, salimos
                }

            } catch (Exception e) {
                System.err.println("Error crítico al recuperar el lote " + pageNumber + ": " + e.getMessage());
                hasMorePages = false; // Interrumpimos para evitar un bucle infinito en caso de error de BD
            }
        }

        System.out.println("Sincronización global finalizada. Total de películas procesadas: " + totalProcesadas);
    }
}
