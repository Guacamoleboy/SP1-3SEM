package app.controller;

import app.entity.Movie;
import app.service.CompanyService;
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
    private final MovieService movieService;
    private final MovieTMDBService movieTMDBService;
    private final GenreService genreService;
    private final GenreTMDBService genreTMDBService;
    private final GenreSyncService genreSyncService;
    private final CompanyService companyService;

    // _______________________________________________

    public MovieController(EntityManager em) {

        // Sync (Cache)
        this.genreService = new GenreService(em);
        this.genreSyncService = new GenreSyncService(this.genreService);
        this.movieTMDBService = new MovieTMDBService();
        this.genreTMDBService = new GenreTMDBService();
        this.companyService = new CompanyService(em);
        this.movieService = new MovieService(em, this.genreSyncService, this.companyService);

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

    public void getAllMoviesDB() {
        List<Movie> allMovies = movieService.getAllMoviesFromDB();
        for (Movie m : allMovies) {
            if (m.getMovieInfo() == null) {
                System.out.println("Movie ID " + m.getId() + " has no MovieInfo attached");
            }  else {
                System.out.println("Movie from DB: " + m.getMovieInfo().getTitle());
            }
        }
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