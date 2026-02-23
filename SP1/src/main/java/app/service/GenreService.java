package app.service;

import app.dao.GenreDAO;
import app.entity.Genre;
import jakarta.persistence.EntityManager;

public class GenreService extends EntityManagerService<Genre> {

    // Attributes
    private final GenreDAO genreDAO;

    // _________________________________________________________
    // Initial super constructor + type cast

    public GenreService(EntityManager em){
        super(new GenreDAO(em), Genre.class);
        this.genreDAO = (GenreDAO) this.entityManagerDAO;
    }



}