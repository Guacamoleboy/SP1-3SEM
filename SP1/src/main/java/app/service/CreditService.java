package app.service;

import app.dao.CastDAO;
import app.dao.CrewDAO;
import app.dto.external.MovieCreditsTMDBDTO;
import app.entity.Crew;
import app.entity.Movie;
import app.service.external.CreditsTMDBService;
import jakarta.persistence.EntityManager;

public class CreditService {

    // Attributes
    private final CastService castService;
    private final CrewService crewService;
    private final CreditsTMDBService creditsTMDBService;

    // ______________________________________________________________

    public CreditService(EntityManager em){
        this.castService = new CastService(em);
        this.crewService = new CrewService(em);
        this.creditsTMDBService = new CreditsTMDBService();
    }

    // ______________________________________________________________

    public void saveMovieCredits(Movie movie) {
        if (movie == null) return;
        MovieCreditsTMDBDTO creditsDTO = creditsTMDBService.getCreditsByMovieId(movie.getId()).join();
        if (creditsDTO == null) return;
        castService.saveCastsForMovie(creditsDTO.getCast(), movie);
        crewService.saveCrewsForMovie(creditsDTO.getCrew(), movie);
    }

}