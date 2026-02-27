package app.dao;

import app.entity.Cast;
import jakarta.persistence.EntityManager;

public class CastDAO extends EntityManagerDAO<Cast> {

    // Attributes

    // _______________________________________________________________

    public CastDAO(EntityManager em) {
        super(em, Cast.class);
    }

    // _______________________________________________________________


}