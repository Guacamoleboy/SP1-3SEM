package app.dao;

import app.entity.Rating;
import jakarta.persistence.EntityManager;

public class RatingDAO extends EntityManagerDAO<Rating> {

    // Attributes

    // _______________________________________________________________

    public RatingDAO(EntityManager em) {
        super(em, Rating.class);
    }

    // _______________________________________________________________


}