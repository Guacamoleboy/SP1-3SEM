package app.dao;

import app.entity.MovieInfo;
import jakarta.persistence.EntityManager;

public class MovieInfoDAO extends EntityManagerDAO<MovieInfo> {

    // Attributes

    // _______________________________________________________________

    public MovieInfoDAO(EntityManager em) {
        super(em, MovieInfo.class);
    }

    // _______________________________________________________________


}