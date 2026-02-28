package app.dao;

import app.entity.Movie;
import app.enums.LanguageEnum;
import jakarta.persistence.EntityManager;
import java.util.List;

public class MovieDAO extends EntityManagerDAO<Movie> {

    // Attributes

    // _______________________________________________________________

    public MovieDAO(EntityManager em) {
        super(em, Movie.class);
    }

    // _______________________________________________________________

    public List<Movie> searchMoviesByTitleContainsIgnoreCase(String title) {
        String trimmedTitle = title.trim();
        String JPQLContains = "SELECT x FROM Movie x WHERE LOWER(x.movieInfo.title) LIKE LOWER(CONCAT('%', :title, '%'))";
        String JPQLFull = "SELECT x FROM Movie x WHERE x.movieInfo.title = :title";

        // Full Title First
        List<Movie> results = em.createQuery(JPQLFull, Movie.class)
                .setParameter("title", trimmedTitle)
                .getResultList();

        // Contains after
        if (results.isEmpty()) {
            results = em.createQuery(JPQLContains, Movie.class)
                    .setParameter("title", trimmedTitle)
                    .getResultList();
        }

        return results;
    }

    // _______________________________________________________________

    public List<Movie> getAllDanishMovies(String language) {
        String JPQL = "SELECT x FROM Movie x WHERE x.movieInfo.originalLanguage = :lang";
        return em.createQuery(JPQL, Movie.class)
                .setParameter("lang", language)
                .getResultList();
    }

    // _______________________________________________________________

    public List<Movie> getMoviesByGenreId(Integer genreId) {
        String JPQL = "SELECT x FROM Movie x JOIN x.genre y WHERE y.id = :genreId";
        return em.createQuery(JPQL, Movie.class)
                .setParameter("genreId", genreId)
                .getResultList();
    }

}