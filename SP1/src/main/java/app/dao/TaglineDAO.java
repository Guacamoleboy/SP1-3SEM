package app.dao;

import app.entity.Tagline;
import jakarta.persistence.EntityManager;

public class TaglineDAO extends EntityManagerDAO<Tagline> {

    // Attributes

    // _______________________________________________________________

    public TaglineDAO(EntityManager em) {
        super(em, Tagline.class);
    }

    // _______________________________________________________________


}