package app.service;

import app.dao.CrewDAO;
import app.dto.external.CrewTMDBDTO;
import app.entity.Crew;
import app.entity.Movie;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CrewService extends EntityManagerService<Crew> {

    // Attributes
    private final CrewDAO crewDAO;

    // ______________________________________________________________

    public CrewService(EntityManager em) {
        super(new CrewDAO(em), Crew.class);
        this.crewDAO = (CrewDAO) this.entityManagerDAO;
    }

    // ______________________________________________________________

    public void saveCrewsForMovie(List<CrewTMDBDTO> crewDTOs, Movie movie) {
        if (crewDTOs == null || movie == null) return;

        if (movie.getMovieInfo() != null && movie.getMovieInfo().getCrews() != null
                && !movie.getMovieInfo().getCrews().isEmpty()) {
            System.out.println("Crews already loaded for movie: " + movie.getMovieInfo().getTitle());
            return;
        }

        for (CrewTMDBDTO dto : crewDTOs) {
            Crew crew = new Crew(dto, movie);
            crewDAO.update(crew);
        }
    }

}