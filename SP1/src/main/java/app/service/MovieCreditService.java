package app.service;

import app.dto.external.MovieCreditsTMDBDTO;
import app.entity.Cast;
import app.entity.Crew;
import app.entity.Movie;
import app.service.external.CreditsTMDBService;
import java.util.List;
import java.util.stream.Collectors;

public class MovieCreditService {

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
                    Crew crew = new Crew();
                    crew.setId(dto.getId());
                    crew.setName(dto.getName());
                    crew.setJob(dto.getJob());
                    crew.setDepartment(dto.getDepartment());
                    crew.setGender(dto.getGender());
                    crew.setCreditId(dto.getCreditId());
                    crew.setMovie(movie);
                    return crew;
                }).collect(Collectors.toList());
    }

    // _____________________________________________________________________________________

    public List<Cast> getCast(MovieCreditsTMDBDTO creditsDTO, Movie movie) {
        return creditsDTO.getCast().stream()
                .map(dto -> {
                    Cast cast = new Cast();
                    cast.setId(dto.getId());
                    cast.setName(dto.getName());
                    cast.setCharacter(dto.getCharacter());
                    cast.setGender(dto.getGender());
                    cast.setCreditId(dto.getCreditId());
                    cast.setOrder(dto.getOrder());
                    cast.setMovie(movie);
                    return cast;
                }).collect(Collectors.toList());
    }

}