package app.service;

import app.dao.MovieDAO;
import app.entity.Movie;
import app.enums.CreditTitleEnum;
import jakarta.persistence.EntityManager;
import java.util.*;

public class MovieService extends EntityManagerService<Movie> {

    // Attributes
    private final MovieDAO movieDAO;
    private final List<Movie> movieList; // Cache to prevent multi DB call for no reason

    // _____________________________________________

    public MovieService(EntityManager em){
        super(new MovieDAO(em), Movie.class);
        this.movieDAO = (MovieDAO) this.entityManagerDAO;
        this.movieList = movieDAO.getAll();
    }

    // _____________________________________________

    public Map<String, Double> getMovieRatingAndTitle(){
        // Save title + Average Vote for each movie
        Map<String, Double> ratingValues = new HashMap<>();
        // For-each loop over movieList
        for (Movie movie : movieList) {
            if (movie.getRating() != null && movie.getMovieInfo() != null) {
                ratingValues.put(movie.getMovieInfo().getTitle(), movie.getRating().getVoteAverage());
            }
        }
        return ratingValues;
    }

    // _____________________________________________

    public Map<String, Double> sort(String sortType) {
        // "asc" or "desc" as input
        // default is ascending unless other is specified.
        boolean ascending;

        switch (sortType.toLowerCase()) {
            case "asc":
                ascending = true;
                break;
            case "desc":
                ascending = false;
                break;
            default:
                return null;
        }

        // Unaranged Sort
        Map<String, Double> ratingValues = getMovieRatingAndTitle();
        // Aranged sort
        Map<String, Double> sortedMap = new LinkedHashMap<>();
        // Sort using lambda stream compare a -> b
        ratingValues.entrySet()
                .stream()
                .sorted((a, b) -> ascending ?
                        a.getValue().compareTo(b.getValue()) :
                        b.getValue().compareTo(a.getValue()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }

    // _____________________________________________
    // Functionality of Backend - Bonus (1)
    // ____________________________________
    //
    // Should be able to get movies a specific actor has been part of

    public List<Movie> sortMoviesByActor(Long actorId) {

        // Pre-validation
        if (actorId == null || actorId <= 0L) {
            throw new IllegalArgumentException("Actor ID must be a positive number and cannot be null.");
        }

        // Validation + Lambda sort on movie & cast
        return movieList.stream()
                .filter(movie -> movie.getMovieInfo() != null && movie.getMovieInfo().getCasts() != null)
                .filter(movie -> movie.getMovieInfo().getCasts().stream()
                .anyMatch(cast -> cast.getId().equals(actorId)))
                .toList();
    }

    // _____________________________________________

    public List<Movie> sortMoviesByCrew(Long crewId) {
        // Pre-validation
        if (crewId == null || crewId <= 0L) {
            throw new IllegalArgumentException("Crew ID must be a positive number and cannot be null.");
        }

        // Validation + Lambda filter på movie & crew
        return movieList.stream()
                .filter(movie -> movie.getMovieInfo() != null && movie.getMovieInfo().getCrews() != null)
                .filter(movie -> movie.getMovieInfo().getCrews().stream()
                .anyMatch(crew -> crew.getId().equals(crewId)))
                .toList();
    }

    // _____________________________________________
    // Functionality of Backend - Bonus (2)
    // ____________________________________
    //
    // Should be able to get movies a director has directed

    public List<Movie> getMoviesByDirector(Long crewId) {
        // Pre-validation
        if (crewId == null || crewId <= 0L) {
            throw new IllegalArgumentException("Crew ID must be a positive number.");
        }

        // Only DIRECTORs using our Enum class.
        return movieList.stream()
                .filter(movie -> movie.getMovieInfo() != null && movie.getMovieInfo().getCrews() != null)
                .filter(movie -> movie.getMovieInfo().getCrews().stream()
                .anyMatch(crew -> crew.getId().equals(crewId) &&
                    crew.getJob().equalsIgnoreCase(CreditTitleEnum.DIRECTOR.name())))
                .toList();
    }

    // _____________________________________________
    // Functionality of Backend - Bonus (3)
    // ____________________________________
    //
    // Should be able to call TMDB API in order to check if new movies has been added since last getAll() call
    //
    // Explaining attributes:
    //
    // apiMovies -> Movie Objects we just got from our getDanishMovies() method
    // apiIds -> List of IDs from TMDB Fetch to check against local storage
    // movieList -> Curent cached local storage

    public void syncWithTmdb(List<Long> apiIds, List<Movie> apiMovies) {

        // Danish movies only now else we would make 1200 calls to their API if we used getById()
        // TODO: Find a method that returns all TMDB Movies (?) and then use that with page=?
        // TODO: Else we would hit rate limit instantly.

        // Remove if no longer present in API Fetch
        List<Movie> toRemove = new ArrayList<>();
        for (Movie movie : movieList) {
            if (!apiIds.contains(movie.getId())) {
                toRemove.add(movie);
            }
        }

        // Delete
        for (Movie movieDB : toRemove) {
            movieDAO.delete(movieDB);
        }

        // Add Movies
        List<Movie> toAdd = new ArrayList<>();
        for (Movie movieFetch : apiMovies) {

            // Locally = Our DB
            boolean existsLocally = false;

            // For-each over DB movies vs movieList (cache)
            for (Movie movieDB : movieList) {
                if (movieDB.getId().equals(movieFetch.getId())) {
                    existsLocally = true;
                    break;
                }
            }

            // If not found. Add it.
            if (!existsLocally) {
                toAdd.add(movieFetch);
            }

        }

        // Create
        for (Movie movie : toAdd) {
            movieDAO.create(movie);
        }

        // Cache update
        refreshMovieCache();
    }

    // _____________________________________________

    private void refreshMovieCache() {
        this.movieList.clear();
        this.movieList.addAll(movieDAO.getAll());
    }

}