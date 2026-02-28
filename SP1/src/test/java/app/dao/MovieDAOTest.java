package app.dao;

import app.ASetup;
import app.entity.Genre;
import app.entity.Movie;
import app.entity.MovieInfo;
import org.junit.jupiter.api.*;
import app.enums.LanguageEnum;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieDAOTest extends ASetup {

    // Attributes
    private MovieDAO movieDAO;

    // __________________________________________________________________________________

    @BeforeEach
    public void setupDAO() {
        movieDAO = new MovieDAO(em);

        // Genre
        Genre actionGenre = Genre.builder()
                .id(28)
                .genreName("Action")
                .build();
        Genre comedyGenre = Genre.builder()
                .id(35)
                .genreName("Comedy")
                .build();

        // MovieInfo
        MovieInfo movieInfoSwedish = MovieInfo.builder()
                .title("Swedish Title")
                .originalLanguage(LanguageEnum.SWEDEN)
                .build();
        MovieInfo movieInfoDanish = MovieInfo.builder()
                .title("Danish Title")
                .originalLanguage(LanguageEnum.DENMARK)
                .build();
        MovieInfo movieInfoDanish2 = MovieInfo.builder()
                .title("Danish Title Two")
                .originalLanguage(LanguageEnum.DENMARK)
                .build();

        // Movie
        Movie movieDanish = Movie.builder()
                .id(1)
                .movieInfo(movieInfoDanish)
                .genre(List.of(actionGenre))
                .build();
        Movie movieDanishTwo = Movie.builder()
                .id(2)
                .movieInfo(movieInfoDanish2)
                .genre(List.of(actionGenre))
                .build();
        Movie movieSwedish = Movie.builder()
                .id(3)
                .movieInfo(movieInfoSwedish)
                .genre(List.of(comedyGenre))
                .build();

        em.persist(actionGenre);
        em.persist(comedyGenre);
        em.persist(movieDanish);
        em.persist(movieDanishTwo);
        em.persist(movieSwedish);

    }

    // __________________________________________________________________________________

    @Test
    @DisplayName("Should return exactly one movie on full title match")
    public void shouldReturnFullTitle() {
        // Act
        List<Movie> results = movieDAO.searchMoviesByTitleContainsIgnoreCase("Danish Title");
        // Assert
        assertEquals(1, results.size());
        assertEquals("Danish Title", results.get(0).getMovieInfo().getTitle());
        assertEquals(1, results.get(0).getId());
    }

    // __________________________________________________________________________________

    @Test
    @DisplayName("Should return all movies containing a search string")
    public void shouldReturnAllContainingTitle() {
        // Act
        List<Movie> results = movieDAO.searchMoviesByTitleContainsIgnoreCase("Title");
        // Assert
        assertEquals(3, results.size());
    }

    // __________________________________________________________________________________

    @Test
    @DisplayName("Should return all movies for a specific genre ID")
    public void shouldReturnMoviesByGenreId() {
        // Genre ACTION
        List<Movie> results = movieDAO.getMoviesByGenreId(28);
        assertEquals(2, results.size());
        // Genre COMEDY
        List<Movie> comedyResults = movieDAO.getMoviesByGenreId(35);
        assertEquals(1, comedyResults.size());
        assertEquals(3, comedyResults.get(0).getId());
    }

    // __________________________________________________________________________________

    @Test
    @DisplayName("Should return empty list if no movies found")
    public void shouldReturnEmptyListIfNoMatch() {
        // Act
        List<Movie> results = movieDAO.searchMoviesByTitleContainsIgnoreCase("NonExistent");
        // Assert
        assertTrue(results.isEmpty());
    }

}