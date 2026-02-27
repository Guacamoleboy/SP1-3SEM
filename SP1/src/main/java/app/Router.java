package app;

import app.controller.CompanyController;
import app.controller.GenreController;
import app.controller.MovieController;
import app.config.HibernateConfig;
import app.controller.UserController;
import app.entity.Company;
import app.entity.Genre;
import app.entity.Movie;
import app.service.CreditService;
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

            // FOB [2]
            movieController.getAllMoviesDB();

            // FOB [3]
            // Could be refactored to using IDs from DB movies, but done like this for now.
            Movie movieCreditMovie1 = movieController.getMovieById(980026);
            Movie movieCreditMovie2 = movieController.getMovieById(1541356);
            CreditService creditService = new CreditService(em);
            creditService.saveMovieCredits(movieCreditMovie1);
            creditService.saveMovieCredits(movieCreditMovie2);

            // FOB [4]
            Integer genreIdAction = 28;
            Integer genreIdComedy = 35;
            movieController.getMoviesByGenre(genreIdAction);
            movieController.getMoviesByGenre(genreIdComedy);

            // FOB [5]
            // Already done. Use EntityManagerDAO and create() method on a Movie movie.

            // FOB [6]
            for (String term : new String[] { "salmon", "MANGO", "Mr. Nob", "Mr. Nobody Against PUTIN", "ab" }) {
                movieController.searchMoviesByTitle(term);
            }

            // FOB [7]
            movieController.getTop10("asc");
            movieController.getTop10("desc");
            movieController.mostPopular(10);
            movieController.mostPopular(20);

        }

    }
}