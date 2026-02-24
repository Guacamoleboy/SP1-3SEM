package app.service;

import app.dao.MovieDAO;
import app.entity.Movie;
import app.entity.Rating;
import jakarta.persistence.EntityManager;

import java.util.*;

public class MovieService extends EntityManagerService<Movie> {

    // Attributes
    private final MovieDAO movieDAO;

    // _____________________________________________

    public MovieService(EntityManager em){
        super(new MovieDAO(em), Movie.class);
        this.movieDAO = (MovieDAO) this.entityManagerDAO;
    }

    // _____________________________________________

    public Map<String, Double> getMovieRatingAndTitle(){
        List<Movie> movieList = movieDAO.getAll();
        // Save title + Average Vote for each movie
        Map<String, Double> ratingValues = new HashMap<>();
        // For-each loop over movieList
        for (Movie movie : movieList) {
            Rating rating = movie.getRating();
            ratingValues.put(movie.getMovieInfo().getTitle(), rating.getVoteAverage());
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

    public List<Movie> sortMoviesByActor(Long actorId){

        // Pre-validation
        if (actorId == null || actorId = 0L) {

        }

    }

}