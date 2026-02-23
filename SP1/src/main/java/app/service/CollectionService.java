package app.service;

import app.dao.CollectionDAO;
import app.entity.Collection;
import jakarta.persistence.EntityManager;

public class CollectionService extends EntityManagerService<Collection> {

    // Attributes
    private final CollectionDAO collectionDAO;

    // _________________________________________________________
    // Initial super constructor + type cast

    public CollectionService(EntityManager em){
        super(new CollectionDAO(em), Collection.class);
        this.collectionDAO = (CollectionDAO) this.entityManagerDAO;
    }




}