package app.dao;

import app.dto.external.MoviePageTMDBDTO;
import app.entity.Movie;
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
        return em.createQuery("SELECT m FROM Movie m " + "WHERE LOWER(m.movieInfo.title) LIKE LOWER(CONCAT('%', :title, '%'))", Movie.class)
                .setParameter("title", title)
                .getResultList();
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

    // _______________________________________________________________

    public void saveAll(List<Movie> movies) {
        try {
            em.getTransaction().begin();
            for (Movie movie : movies) {
                em.merge(movie);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        }
    }

}