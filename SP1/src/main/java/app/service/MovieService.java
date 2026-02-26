package app.service;

import app.dao.MovieDAO;
import app.dto.external.MovieTMDBDTO;
import app.entity.Movie;
import app.enums.CreditTitleEnum;
import app.enums.LanguageEnum;
import app.service.converter.MovieConverter;
import app.service.external.MovieTMDBService;
import jakarta.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

import app.entity.Genre;

public class MovieService extends EntityManagerService<Movie> {

    // Attributes
    private final MovieDAO movieDAO;
    private final List<Movie> movieList; // Cache to prevent multi DB call for no reason
    private final MovieTMDBService movieTMDBService = new MovieTMDBService();

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

    public void syncDanishMoviesFromApi(Long years) {
        movieTMDBService.getDanishMoviesByRelease(years, 1)
                .thenAccept(pageDTO -> {
                    syncWithTmdb(pageDTO.getResults());
                    System.out.println("Sync gennemført for de sidste " + years + " år.");
                })
                .exceptionally(ex -> {
                    System.err.println("Fejl under sync: " + ex.getMessage());
                    return null;
                });
    }

    // _____________________________________________

    public void fetchAndSaveDanishMovies(Long years, int page) {
        movieTMDBService.getDanishMoviesByRelease(years, page)
                .thenAccept(pageDTO -> {
                    List<MovieTMDBDTO> dtos = pageDTO.getResults();
                    if (dtos != null && !dtos.isEmpty()) {
                        syncWithTmdb(dtos);
                        System.out.println("Sync fuldført for " + dtos.size() + " film.");
                    }
                })
                .exceptionally(ex -> {
                    System.err.println("Fejl i sync flow: " + ex.getMessage());
                    return null;
                });
    }

    // _____________________________________________

    /*
    public void fetchMultiplePages(Long years, int totalPages) {
        for (int i = 1; i <= totalPages; i++) {
            final int currentPage = i;
            movieTMDBService.getDanishMoviesByRelease(years, currentPage)
                    .thenAccept(pageDTO -> {
                        syncWithTmdb(pageDTO.getResults());
                        System.out.println("Hentet side " + currentPage);
                    });
        }
    }
    */

    // _____________________________________________

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

    public void syncWithTmdb(List<MovieTMDBDTO> apiDtos) {

        List<Movie> apiMovies = MovieConverter.toEntityList(apiDtos);

        List<Long> apiIds = new ArrayList<>();
        for (Movie m : apiMovies) {
            apiIds.add(m.getId());
        }

        List<Movie> toRemove = new ArrayList<>();
        for (Movie dbMovie : movieList) {
            if (!apiIds.contains(dbMovie.getId())) {
                toRemove.add(dbMovie);
            }
        }

        for (Movie m : toRemove) {
            movieDAO.delete(m);
        }

        List<Movie> toAdd = new ArrayList<>();
        for (Movie apiMovie : apiMovies) {
            boolean existsInDb = false;
            for (Movie dbMovie : movieList) {
                if (dbMovie.getId().equals(apiMovie.getId())) {
                    existsInDb = true;
                    break;
                }
            }

            if (!existsInDb) {
                toAdd.add(apiMovie);
            }
        }

        for (Movie m : toAdd) {
            movieDAO.create(m);
        }

        refreshMovieCache();
    }

    // _____________________________________________

    private void refreshMovieCache() {
        this.movieList.clear();
        this.movieList.addAll(movieDAO.getAll());
    }

    // _____________________________________________
    // DB Fetch Danish movies

    public List<Movie> getAllDanishMovies(){
        return movieDAO.getAllDanishMovies(LanguageEnum.DENMARK.getIso639());
    }


}