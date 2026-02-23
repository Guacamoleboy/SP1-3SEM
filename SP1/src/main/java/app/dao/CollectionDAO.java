package app.dao;

import app.entity.Collection;
import jakarta.persistence.EntityManager;

public class CollectionDAO extends EntityManagerDAO<Collection> {

    // Attributes

    // _______________________________________________________________

    public CollectionDAO(EntityManager em) {
        super(em, Collection.class);
    }

    // _______________________________________________________________


}