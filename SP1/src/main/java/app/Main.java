package app;

import app.config.HibernateConfig;
import app.controller.MovieController;
import app.service.external.MovieTMDBService;
import app.util.PopulateDB;
import jakarta.persistence.EntityManager;

public class Main {

    public static void main(String[] args) {

        // TODO: Jonas - 25/02-2026
        // TODO: ___________________
        // TODO:
        // TODO: Create a implementation in Controller that does this instead of having to use main as entry point
        // TODO: for all controllers?
        /*EntityManager em = HibernateConfig.getEntityManagerFactory().createEntityManager();
        MovieController controller = new MovieController(em);*/

        // TODO: Jonas - 25/02-2026
        // TODO: ___________________
        // TODO:
        // TODO: Move to PopulatorDB?
        /*controller.populateDatabase(5L);*/

        /*
        MovieTMDBService movieTMDBService = new MovieTMDBService();
        movieTMDBService.getDanishMoviesByRelease(5L);
        */

        PopulateDB.populateRoles();
        PopulateDB.populateLanguages();

    }

}