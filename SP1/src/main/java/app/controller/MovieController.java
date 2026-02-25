package app.controller;

import app.dto.external.MoviePageTMDBDTO;
import app.entity.Movie;
import app.service.GenreService;
import app.service.MovieService;
import app.service.external.GenreTMDBService;
import app.service.external.MovieTMDBService;
import app.service.converter.MovieConverter;
import app.service.sync.GenreSyncService;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public class MovieController {

    // Attributes
    private final MovieService movieService; // Internal
    private final MovieTMDBService movieTMDBService; // External
    private final GenreService genreService; // Internal
    private final GenreTMDBService genreTMDBService;
    private final GenreSyncService genreSyncService; // Sync

    // _______________________________________________

    public MovieController(EntityManager em) {
        this.movieTMDBService = new MovieTMDBService();
        this.movieService = new MovieService(em);
        this.genreService = new GenreService(em);
        this.genreTMDBService = new GenreTMDBService();
        this.genreSyncService = new GenreSyncService(this.genreService, this.genreTMDBService);
    }

    // _______________________________________________
    // TODO: Fix

    public void getDanishMoviesByRelease(Long years) {
        genreSyncService.genreSyncCheck();
    }

    // _______________________________________________

    public Map<String, Double> getMoviesSortedByRating(String direction) {
        return movieService.sort(direction);
    }

    // _______________________________________________

    public List<Movie> getMoviesByActor(Long actorId) {
        return movieService.sortMoviesByActor(actorId);
    }

    // _______________________________________________

    public List<Movie> getMoviesByDirector(Long directorId) {
        return movieService.getMoviesByDirector(directorId);
    }

}