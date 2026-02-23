package app.service;

import app.dao.CrewDAO;
import app.entity.Crew;
import jakarta.persistence.EntityManager;

public class CrewService extends EntityManagerService<Crew> {

    // Attributes
    private final CrewDAO crewDAO;

    // _________________________________________________________
    // Initial super constructor + type cast

    public CrewService(EntityManager em){
        super(new CrewDAO(em), Crew.class);
        this.crewDAO = (CrewDAO) this.entityManagerDAO;
    }



}