package app.controller;

import app.dto.external.MoviePageTMDBDTO;
import app.entity.Movie;
import app.service.GenreService;
import app.service.MovieService;
import app.service.external.MovieTMDBService;
import app.util.MovieConverter;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public class MovieController {

    // Attributes
    private final MovieService movieService; // Internal
    private final MovieTMDBService movieTMDBService; // External
    private final GenreService genreService; // Internal
    // TODO: HashMap for Genres.

    public MovieController(EntityManager em) {
        this.movieTMDBService = new MovieTMDBService();
        this.movieService = new MovieService(em);
        this.genreService = new GenreService(em);
    }

    // _______________________________________________
    // Step 1: Save genres to DB first
    // Step 2: Fetch movies, attach persisted genres, save movies
    // Genres must exist before movies due to @ManyToMany constraint

    // Should only be used once in a while.
    // TODO: Store Genres in a HashMap as global attribute instead of having to call TMBD each time
    // TODO: we want a genre. Genres don't change often. No need to call TMDB unless it's neeeded. (rate limit)
    public void getDanishMoviesByRelease(Long years) {

        // First we get all genres
        genreService.getAllGenresFromTMDB();

        // Then we get all Danish Movies
        MoviePageTMDBDTO page = movieTMDBService.getDanishMoviesByRelease(years).join();
        List<Movie> movies = MovieConverter.toEntityList(page.getResults());
        movieService.persistMovies(movies, genreService);
        System.out.println("Stored " + movies.size() + " movies.");
    }

    // _______________________________________________
    // Sync DB with TMDB - adds new, removes deleted

    // TODO: Jonas - 25/02-2026
    // TODO: ___________________
    // TODO:
    // TODO: Move to PopulatorDB?

    public void syncDatabase(Long years) {
        MoviePageTMDBDTO page = movieTMDBService.getDanishMoviesByRelease(years).join();
        List<Movie> apiMovies = MovieConverter.toEntityList(page.getResults());
        List<Long> apiIds = apiMovies.stream().map(Movie::getId).toList();
        movieService.syncWithTmdb(apiIds, apiMovies);
        System.out.println("Sync complete.");
    }

    // _______________________________________________
    // Fetch and store a single movie by TMDB ID

    public void fetchAndStoreMovieById(Long id) {
        movieTMDBService.getMovieById(id)
                .thenAccept(dto -> {
                    Movie movie = MovieConverter.toEntity(dto);
                    movieService.create(movie);
                    System.out.println("Stored movie: " + id);
                }).join();
    }

    // _______________________________________________
    // Get movies sorted by rating ("asc" or "desc")

    public Map<String, Double> getMoviesSortedByRating(String direction) {
        return movieService.sort(direction);
    }

    // _______________________________________________
    // Get movies a specific actor has appeared in

    public List<Movie> getMoviesByActor(Long actorId) {
        return movieService.sortMoviesByActor(actorId);
    }

    // _______________________________________________
    // Get movies a specific director has directed

    public List<Movie> getMoviesByDirector(Long directorId) {
        return movieService.getMoviesByDirector(directorId);
    }
}