package app.service;

import app.dao.MovieDAO;
import app.entity.Movie;
import jakarta.persistence.EntityManager;

public class MovieService extends EntityManagerService<Movie> {

    // Attributes
    private final MovieDAO movieDAO;

    // _________________________________________________________
    // Initial super constructor + type cast

    public MovieService(EntityManager em){
        super(new MovieDAO(em), Movie.class);
        this.movieDAO = (MovieDAO) this.entityManagerDAO;
    }



}