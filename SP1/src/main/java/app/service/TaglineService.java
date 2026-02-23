package app.service;

import app.dao.TaglineDAO;
import app.entity.Tagline;
import jakarta.persistence.EntityManager;

public class TaglineService extends EntityManagerService<Tagline> {

    // Attributes
    private final TaglineDAO taglineDAO;

    // _________________________________________________________
    // Initial super constructor + type cast

    public TaglineService(EntityManager em){
        super(new TaglineDAO(em), Tagline.class);
        this.taglineDAO = (TaglineDAO) this.entityManagerDAO;
    }



}