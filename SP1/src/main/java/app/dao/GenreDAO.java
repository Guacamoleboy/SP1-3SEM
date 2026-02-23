package app.dao;

import app.entity.Genre;
import jakarta.persistence.EntityManager;

public class GenreDAO extends EntityManagerDAO<Genre> {

    // Attributes

    // _______________________________________________________________

    public GenreDAO(EntityManager em) {
        super(em, Genre.class);
    }

    // _______________________________________________________________


}