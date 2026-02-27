package app.controller;

import app.entity.Movie;
import app.service.CreditService;
import jakarta.persistence.EntityManager;

public class CreditController {

    // Attributes
    private final CreditService creditService;

    // ___________________________________________________________________

    public CreditController(EntityManager em){
        this.creditService = new CreditService(em);
    }

    // ___________________________________________________________________

    public void saveMovieCredits(Movie movie){
        creditService.saveMovieCredits(movie);
    }

}