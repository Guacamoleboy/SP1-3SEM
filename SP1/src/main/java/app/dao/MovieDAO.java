package app.dao;

import app.entity.Movie;
import jakarta.persistence.EntityManager;

public class MovieDAO extends EntityManagerDAO<Movie> {

    // Attributes

    // _______________________________________________________________

    public MovieDAO(EntityManager em) {
        super(em, Movie.class);
    }

    // _______________________________________________________________


}