package app.service;

import app.ASetup;
import app.dto.external.CastTMDBDTO;
import app.entity.Cast;
import app.entity.Movie;
import app.entity.MovieInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CastServiceTest extends ASetup {

    // Attributes
    private CastService castService;
    private Movie movie;
    private CastTMDBDTO actor;
    private CastTMDBDTO actorTwo;

    // _____________________________________________________________________________

    @BeforeEach
    public void setUpService() {
        castService = new CastService(em);

        MovieInfo info = MovieInfo.builder()
                .title("American Pie 3")
                .casts(new ArrayList<>())
                .build();

        movie = Movie.builder()
                .id(500)
                .movieInfo(info)
                .build();

        em.persist(movie);

        actor = CastTMDBDTO.builder()
                .id(10)
                .name("Jonas")
                .character("Nerd")
                .build();

        actorTwo = CastTMDBDTO.builder()
                .id(20)
                .name("Drake")
                .character("Yup")
                .build();
    }

    // _____________________________________________________________________________

    @Test
    @DisplayName("Should add casts")
    void shouldAddCasts() {
        // Act
        castService.saveCastsForMovie(List.of(actor), movie);
        // Act
        Cast saved = castService.getById(10);
        // Assert
        assertNotNull(saved);
        assertEquals("Jonas", saved.getName());
    }

    // _____________________________________________________________________________

    @Test
    @DisplayName("Should skip if movie already has casts in MovieInfo")
    void shouldSkipIfAlreadyLoaded() {
        Cast newCast = Cast.builder()
                .id(999)
                .character("None")
                .movie(movie)
                .build();
        // Act
        em.persist(newCast);
        movie.getMovieInfo().getCasts().add(newCast);
        castService.saveCastsForMovie(List.of(actorTwo), movie);
        // Assert
        assertFalse(castService.existByColumn("Drake", "name"));
    }

    // _____________________________________________________________________________

    @Test
    @DisplayName("Should update cast column by ID")
    void shouldUpdateColumnById() {
        // Arrange
        Cast cast = new Cast(actor, movie);
        castService.create(cast);
        // Act
        castService.updateColumnById(10, "character", "Diddy");
        em.clear();
        // Assert
        Cast updated = castService.getById(10);
        assertEquals("Diddy", updated.getCharacter());
    }

    // _____________________________________________________________________________

    @Test
    @DisplayName("Should delete a cast by ID")
    void shouldDeleteById() {
        // Arrange
        Cast cast = new Cast(actor, movie);
        castService.create(cast);
        // Act
        castService.deleteById(10);
        // Assert
        assertNull(castService.getById(10));
    }

}