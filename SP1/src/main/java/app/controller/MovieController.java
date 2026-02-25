package app.controller;

import app.dao.GenreDAO;
import app.dto.external.GenreTMDBDTO;
import app.dto.external.MoviePageTMDBDTO;
import app.entity.Genre;
import app.entity.Movie;
import app.service.MovieService;
import app.service.external.GenreTMDBService;
import app.service.external.MovieTMDBService;
import app.util.MovieConverter;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public class MovieController {

    private final MovieService movieService;
    private final MovieTMDBService movieTMDBService;
    private final GenreTMDBService genreTMDBService;
    private final GenreDAO genreDAO;

    public MovieController(EntityManager em) {
        this.movieTMDBService = new MovieTMDBService();
        this.genreTMDBService = new GenreTMDBService();
        this.movieService = new MovieService(em);
        this.genreDAO = new GenreDAO(em);
    }

    // _______________________________________________
    // Step 1: Save genres to DB first
    // Step 2: Fetch movies, attach persisted genres, save movies
    // Genres must exist before movies due to @ManyToMany constraint

    public void populateDatabase(Long years) {

        // Step 1: Save genres
        List<GenreTMDBDTO> genreDTOs = genreTMDBService.getAllGenres().join();
        for (GenreTMDBDTO dto : genreDTOs) {
            Genre genre = Genre.builder()
                    .id(dto.getId())
                    .genreName(dto.getName())
                    .build();
            if (!genreDAO.existByColumn(dto.getId(), "id")) {
                genreDAO.create(genre);
            }
        }
        System.out.println("Stored " + genreDTOs.size() + " genres.");

        // Step 2: Fetch and save movies
        MoviePageTMDBDTO page = movieTMDBService.getDanishMoviesByRelease(years).join();
        List<Movie> movies = MovieConverter.toEntityList(page.getResults());

        for (Movie movie : movies) {
            // Swap DTO-built genres for persisted DB genres to avoid duplicate key errors
            if (movie.getGenre() != null) {
                List<Genre> persistedGenres = movie.getGenre().stream()
                        .map(g -> genreDAO.getById(g.getId()))
                        .filter(g -> g != null)
                        .toList();
                movie.setGenre(persistedGenres);
            }

            // Only insert if this movie doesn't already exist in the DB
            if (!movieService.existByColumn(movie.getMovieInfo().getTmdbId(), "movieInfo.tmdbId")) {
            }
        }
        System.out.println("Stored " + movies.size() + " movies.");
    }

    // _______________________________________________
    // Sync DB with TMDB - adds new, removes deleted

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