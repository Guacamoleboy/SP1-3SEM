package app;

import app.controller.GenreController;
import app.controller.MovieController;
import app.dao.MovieDAO;
import app.dao.GenreDAO;
import app.config.HibernateConfig;
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

            // GENRE
            //
            GenreController genreController = new GenreController(em);
            genreController.downloadGenresFromTMDB();
            Genre genreTest = genreController.getGenreById(28L);
            System.out.println("\nGenre: " + genreTest.getGenreName());

            // MOVIE
            //
            MovieController movieController = new MovieController(em);
            Long clientReturn = 5L;
            movieController.getDanishMoviesByRelease(clientReturn);
            // movieController.deleteAllMovies();

        }

    }
}