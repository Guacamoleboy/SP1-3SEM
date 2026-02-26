package app.service;

import app.config.HibernateConfig;
import app.entity.*;
import app.enums.CreditTitleEnum;
import app.enums.LanguageEnum;
import app.service.sync.GenreSyncService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class MovieServiceTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private MovieService movieService;

    // ______________________________________________

    @BeforeAll
    static void setupAll() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
    }

    // ______________________________________________

    @BeforeEach
    void setup() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.clear();

        GenreService genreService = new GenreService(em);
        CompanyService companyService = new CompanyService(em);
        GenreSyncService genreSyncService = new GenreSyncService(genreService);

        movieService = new MovieService(em, genreSyncService, companyService);
    }

    // ______________________________________________

    @AfterEach
    void cleanup() {
        if (em != null && em.isOpen()) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    // ______________________________________________

    @AfterAll
    static void closeAll() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    // --------------------------------------------------

    private void createMovie(Integer id, String title, Double ratingValue) {

        Rating rating = new Rating();
        rating.setVoteAverage(ratingValue);

        MovieInfo movieInfo = new MovieInfo();
        movieInfo.setTitle(title);
        movieInfo.setOriginalLanguage(LanguageEnum.DENMARK);
        movieInfo.setImdbId("200");
        movieInfo.setReleaseDate(LocalDate.now());

        Movie movie = new Movie();
        movie.setId(id);
        movie.setMovieInfo(movieInfo);
        movie.setRating(rating);

        em.persist(movie);
    }

    // --------------------------------------------------

    @Test
    void getMovieRatingAndTitle() {

        createMovie(1, "Test Movie", 5.0);
        em.flush();

        Map<String, Double> result = movieService.getMovieRatingAndTitle();

        assertEquals(1, result.size());
        assertEquals(5.0, result.get("Test Movie"));
    }

    // --------------------------------------------------

    @Test
    void sortAscending() {

        createMovie(1, "Movie A", 1.0);
        createMovie(2, "Movie B", 1.1);
        em.flush();

        Map<String, Double> sorted = movieService.sort("asc");

        Double firstValue = sorted.values().iterator().next();
        assertEquals(1.0, firstValue);
    }

    // --------------------------------------------------

    @Test
    void sortDescending() {

        createMovie(1, "Movie A", 1.0);
        createMovie(2, "Movie B", 1.1);
        em.flush();

        Map<String, Double> sorted = movieService.sort("desc");

        Double firstValue = sorted.values().iterator().next();
        assertEquals(1.1, firstValue);
    }

    // --------------------------------------------------

    @Test
    void sortMoviesByActor() {

        Cast actor = new Cast();
        actor.setId(10);
        actor.setName("Actor Test");

        MovieInfo info = new MovieInfo();
        info.setTitle("Actor Movie");
        info.setOriginalLanguage(LanguageEnum.DENMARK);
        info.setImdbId("200");
        info.setReleaseDate(LocalDate.now());
        info.setCasts(List.of(actor));

        Movie movie = new Movie();
        movie.setId(3);
        movie.setMovieInfo(info);

        em.persist(actor);
        em.persist(movie);
        em.flush();

        List<Movie> result = movieService.sortMoviesByActor(10);

        assertEquals(1, result.size());
    }

    // --------------------------------------------------

    @Test
    void sortMoviesByCrew() {

        Crew crew = new Crew();
        crew.setId(15);
        crew.setName("Crew Test");
        crew.setJob("Producer");

        MovieInfo info = new MovieInfo();
        info.setTitle("Crew Movie");
        info.setOriginalLanguage(LanguageEnum.DENMARK);
        info.setImdbId("250");
        info.setReleaseDate(LocalDate.now());
        info.setCrews(List.of(crew));

        Movie movie = new Movie();
        movie.setId(5);
        movie.setMovieInfo(info);

        em.persist(crew);
        em.persist(movie);
        em.flush();

        List<Movie> result = movieService.sortMoviesByCrew(15);

        assertEquals(1, result.size());
        assertEquals("Crew Movie", result.get(0).getMovieInfo().getTitle());
    }

    // --------------------------------------------------

    @Test
    void getMoviesByDirector() {

        Crew director = new Crew();
        director.setId(20);
        director.setName("Director Test");
        director.setJob(CreditTitleEnum.DIRECTOR.name());

        MovieInfo info = new MovieInfo();
        info.setTitle("Directed Movie");
        info.setOriginalLanguage(LanguageEnum.DENMARK);
        info.setImdbId("300");
        info.setReleaseDate(LocalDate.now());
        info.setCrews(List.of(director));

        Movie movie = new Movie();
        movie.setId(6);
        movie.setMovieInfo(info);

        em.persist(director);
        em.persist(movie);
        em.flush();

        List<Movie> result = movieService.getMoviesByDirector(20);

        assertEquals(1, result.size());
        assertEquals("Directed Movie", result.get(0).getMovieInfo().getTitle());
    }

}