package app;

import app.config.HibernateConfig;
import app.controller.MovieController;
import app.service.external.MovieTMDBService;
import app.util.PopulateDB;
import jakarta.persistence.EntityManager;

public class Main {

    public static void main(String[] args) {

        EntityManager em = HibernateConfig.getEntityManagerFactory().createEntityManager();
        MovieController mc = new MovieController(em);
        mc.getDanishMoviesByRelease();

        PopulateDB.populateRoles();
        PopulateDB.populateLanguages();

    }

}