package app.dao;

import app.entity.Crew;
import jakarta.persistence.EntityManager;

public class CrewDAO extends EntityManagerDAO<Crew> {

    // Attributes

    // _______________________________________________________________

    public CrewDAO(EntityManager em) {
        super(em, Crew.class);
    }

    // _______________________________________________________________


}