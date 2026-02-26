package app.controller;

import app.entity.Movie;
import app.service.GenreService;
import app.service.MovieService;
import app.service.external.GenreTMDBService;
import app.service.external.MovieTMDBService;
import app.service.sync.GenreSyncService;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public class MovieController {

    // Attributes
    private final MovieService movieService;            // Internal
    private final MovieTMDBService movieTMDBService;    // External
    private final GenreService genreService;            // Internal
    private final GenreTMDBService genreTMDBService;    // External
    private final GenreSyncService genreSyncService;    // Sync

    // _______________________________________________

    public MovieController(EntityManager em) {

        // Sync (Cache)
        this.genreService = new GenreService(em);
        this.genreSyncService = new GenreSyncService(this.genreService);

        // External Services
        this.movieTMDBService = new MovieTMDBService();
        this.genreTMDBService = new GenreTMDBService();

        // Internal Services
        this.movieService = new MovieService(em, this.genreSyncService);

    }

    // _______________________________________________

    public void getDanishMoviesByRelease(Integer year) {
        movieService.syncDanishMoviesFromApi(year);
    }

    // _______________________________________________

    public void deleteAllMovies() {
        movieService.deleteAll();
    }

    // _______________________________________________

    public Map<String, Double> getMoviesSortedByRating(String direction) {
        return movieService.sort(direction);
    }

    // _______________________________________________

    public List<Movie> getMoviesByActor(Integer actorId) {
        return movieService.sortMoviesByActor(actorId);
    }

    // _______________________________________________

    public List<Movie> getMoviesByDirector(Integer directorId) {
        return movieService.getMoviesByDirector(directorId);
    }

}