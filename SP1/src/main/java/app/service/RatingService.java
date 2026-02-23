package app.service;

import app.dao.RatingDAO;
import app.entity.Rating;
import jakarta.persistence.EntityManager;

public class RatingService extends EntityManagerService<Rating> {

    // Attributes
    private final RatingDAO RatingDAO;

    // _________________________________________________________
    // Initial super constructor + type cast

    public RatingService(EntityManager em){
        super(new RatingDAO(em), Rating.class);
        this.RatingDAO = (RatingDAO) this.entityManagerDAO;
    }



}