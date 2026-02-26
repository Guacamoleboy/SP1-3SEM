package app.service;

import app.dto.external.MovieCreditsTMDBDTO;
import app.entity.Cast;
import app.entity.Crew;
import app.entity.Movie;
import app.exception.ApiException;
import app.service.external.CreditsTMDBService;

import java.util.List;
import java.util.stream.Collectors;

public class MovieCreditService {

    // TODO: Not done / working.

    // Attributes
    private final CreditsTMDBService creditsTMDBService;

    // _____________________________________________________________________________________

    public MovieCreditService() {
        this.creditsTMDBService = new CreditsTMDBService();
    }

    // _____________________________________________________________________________________

    public MovieCreditsTMDBDTO getCredits(Integer movieId) {
        return creditsTMDBService.getCreditsByMovieId(movieId).join();
    }

    // _____________________________________________________________________________________

    public List<Crew> getCrew(MovieCreditsTMDBDTO creditsDTO, Movie movie) {
        return creditsDTO.getCrew().stream()
                .map(dto -> {
                    Crew c = new Crew();
                    c.setId(dto.getId());
                    c.setName(dto.getName());
                    c.setJob(dto.getJob());
                    c.setDepartment(dto.getDepartment());
                    c.setGender(dto.getGender());
                    c.setCreditId(dto.getCreditId());
                    c.setMovie(movie);
                    return c;
                }).collect(Collectors.toList());
    }

    // _____________________________________________________________________________________

    public List<Cast> getCast(MovieCreditsTMDBDTO creditsDTO, Movie movie) {
        return creditsDTO.getCast().stream()
                .map(dto -> {
                    Cast c = new Cast();
                    c.setId(dto.getId());
                    c.setName(dto.getName());
                    c.setCharacter(dto.getCharacter());
                    c.setGender(dto.getGender());
                    c.setCreditId(dto.getCreditId());
                    c.setOrder(dto.getOrder());
                    c.setMovie(movie);
                    return c;
                }).collect(Collectors.toList());
    }

}