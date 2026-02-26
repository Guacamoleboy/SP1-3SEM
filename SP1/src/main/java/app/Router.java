package app;

import app.controller.CompanyController;
import app.controller.GenreController;
import app.controller.MovieController;
import app.config.HibernateConfig;
import app.controller.UserController;
import app.entity.Company;
import app.entity.Genre;
import app.util.PopulateDB;
import jakarta.persistence.EntityManager;

public class Router {

    // Attributes

    // _______________________________________________________________

    public void run() {

        // Populate DB with ENUM
        PopulateDB.populateRoles();
        PopulateDB.populateLanguages();

        try (EntityManager em = HibernateConfig.getEntityManagerFactory().createEntityManager()) {

            // These need to be ran in sequel why .join() on service calls are added.
            // Probably not the best solution, but I don't have time to find a better one.
            // - Jonas.

            // GENRE
            //
            GenreController genreController = new GenreController(em);
            genreController.downloadGenresFromTMDB();
            Genre genreTest = genreController.getGenreById(28);
            System.out.println("\nGenre: " + genreTest.getGenreName());

            // USER
            //
            UserController userController = new UserController(em);
            userController.placeholderUsers();

            // COMPANY
            //
            CompanyController companyController = new CompanyController(em);
            Integer idDK = 76;
            Integer idRandom = 2;
            Company company1 = companyController.getById(idDK);
            Company company2 = companyController.getById(idRandom);
            System.out.println("\nCompany: " + company1.getName());

            // MOVIE
            //
            MovieController movieController = new MovieController(em);
            Integer clientReturn = 5;
            movieController.getDanishMoviesByRelease(clientReturn);
            // movieController.deleteAllMovies();

        }

    }
}