package app.service;

import app.ASetup;
import app.dto.external.CrewTMDBDTO;
import app.entity.Crew;
import app.entity.Movie;
import app.entity.MovieInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CrewServiceTest extends ASetup {

    // Attributes
    private CrewService crewService;
    private Movie movie;
    private CrewTMDBDTO crewMember;
    private CrewTMDBDTO crewMemberTwo;

    // _____________________________________________________________________________

    @BeforeEach
    public void setUpService() {
        crewService = new CrewService(em);

        MovieInfo info = MovieInfo.builder()
                .title("American Pie 3")
                .crews(new ArrayList<>())
                .build();

        movie = Movie.builder()
                .id(500)
                .movieInfo(info)
                .build();

        em.persist(movie);

        crewMember = CrewTMDBDTO.builder()
                .id(30)
                .name("Jonas")
                .job("Director")
                .department("Directing")
                .build();

        crewMemberTwo = CrewTMDBDTO.builder()
                .id(40)
                .name("Drake")
                .job("Producer")
                .department("Production")
                .build();
    }

    // _____________________________________________________________________________

    @Test
    @DisplayName("Should add crews")
    public void shouldAddCrews() {
        // Act
        crewService.saveCrewsForMovie(List.of(crewMember), movie);

        // Act (Fetch)
        Crew saved = crewService.getById(30);

        // Assert
        assertNotNull(saved);
        assertEquals("Jonas", saved.getName());
        assertEquals("Director", saved.getJob());
    }

    // _____________________________________________________________________________

    @Test
    @DisplayName("Should skip if movie already has crews in MovieInfo")
    public void shouldSkipIfAlreadyLoaded() {
        // Arrange
        Crew existingCrew = Crew.builder()
                .id(999)
                .job("Cameraman")
                .movie(movie)
                .build();
        // Act
        em.persist(existingCrew);
        movie.getMovieInfo().getCrews().add(existingCrew);
        crewService.saveCrewsForMovie(List.of(crewMemberTwo), movie);
        // Assert
        assertFalse(crewService.existByColumn("Drake", "name"));
    }

    // _____________________________________________________________________________

    @Test
    @DisplayName("Should update crew column by ID")
    public void shouldUpdateColumnById() {
        // Arrange
        Crew crew = new Crew(crewMember, movie);
        crewService.create(crew);
        // Act
        crewService.updateColumnById(30, "job", "Executive Director");
        em.clear();
        // Assert
        Crew updated = crewService.getById(30);
        assertEquals("Executive Director", updated.getJob());
    }

    // _____________________________________________________________________________

    @Test
    @DisplayName("Should delete a crew by ID")
    public void shouldDeleteById() {
        // Arrange
        Crew crew = new Crew(crewMember, movie);
        crewService.create(crew);
        // Act
        crewService.deleteById(30);
        em.clear();
        // Assert
        assertNull(crewService.getById(30));
    }

}