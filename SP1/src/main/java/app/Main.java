package app;

import app.config.HibernateConfig;
import app.controller.MovieController;
import app.util.PopulateDB;
import jakarta.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        EntityManager em = HibernateConfig.getEntityManagerFactory().createEntityManager();
        MovieController controller = new MovieController(em);
        controller.populateDatabase(5L);

        PopulateDB.populateRoles();
        PopulateDB.populateLanguages();
    }
}