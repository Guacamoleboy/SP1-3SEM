package app.dao;

import app.entity.Movie;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Map;

public class MovieDAO extends EntityManagerDAO<Movie> {

    // Attributes

    // _______________________________________________________________

    public MovieDAO(EntityManager em) {
        super(em, Movie.class);
    }
    // _______________________________________________________________
    // Fuctionality of Backend (5)
    // __________________________

    public void addMove(Movie movie) {
        //TODO: Make the functionality of adding a movie to the database!!!
    }


    // _______________________________________________________________
    // Fuctionality of Backend (6)
    // __________________________
    //
    // Search movie by Title from Database

    public List<Movie> searchMoviesByTitleContainsIgnoreCase(String title) {
        return em.createQuery("SELECT m FROM Movie m " + "WHERE LOWER(m.movieInfo.title) LIKE LOWER(CONCAT('%', :title, '%'))", Movie.class)
                .setParameter("title", title)
                .getResultList();
    }

}