package app.service;

import app.dao.MovieDAO;
import app.dto.external.MovieCreditsTMDBDTO;
import app.dto.external.MoviePageTMDBDTO;
import app.dto.external.MovieTMDBDTO;
import app.entity.Cast;
import app.entity.Company;
import app.entity.Crew;
import app.entity.Movie;
import app.enums.CreditTitleEnum;
import app.enums.LanguageEnum;
import app.service.converter.MovieConverter;
import app.service.external.MovieTMDBService;
import app.service.sync.GenreSyncService;
import jakarta.persistence.EntityManager;
import java.util.*;

public class MovieService extends EntityManagerService<Movie> {

    // Attributes
    private final MovieDAO movieDAO;
    private final List<Movie> movieList; // Cache to prevent multi DB call for no reason
    private final MovieTMDBService movieTMDBService = new MovieTMDBService();
    private final GenreSyncService genreSyncService;
    private final MovieConverter movieConverter;
    private final CompanyService companyService;
    private final MovieCreditService movieCreditService = new MovieCreditService();

    // _________________________________________________________________________________________________________

    public MovieService(EntityManager em, GenreSyncService genreSyncService, CompanyService companyService){
        super(new MovieDAO(em), Movie.class);
        this.movieDAO = (MovieDAO) this.entityManagerDAO;
        this.movieList = movieDAO.getAll();
        this.genreSyncService = genreSyncService;
        this.companyService = companyService;
        this.movieConverter = new MovieConverter(companyService, genreSyncService);
    }

    // _________________________________________________________________________________________________________

    public Map<String, Double> getMovieRatingAndTitle(){
        // Save title + Average Vote for each movie
        Map<String, Double> ratingValues = new HashMap<>();
        // For-each loop over movieList
        for (Movie movie : movieList) {
            if (movie.getRating() != null && movie.getMovieInfo() != null) {
                ratingValues.put(movie.getMovieInfo().getTitle(), movie.getRating().getVoteAverage());
            }
        }
        return ratingValues;
    }

    // _________________________________________________________________________________________________________

    public Map<String, Double> sort(String sortType) {
        // "asc" or "desc" as input
        // default is ascending unless other is specified.
        boolean ascending;

        switch (sortType.toLowerCase()) {
            case "asc":
                ascending = true;
                break;
            case "desc":
                ascending = false;
                break;
            default:
                return null;
        }

        // Unaranged Sort
        Map<String, Double> ratingValues = getMovieRatingAndTitle();
        // Aranged sort
        Map<String, Double> sortedMap = new LinkedHashMap<>();
        // Sort using lambda stream compare a -> b
        ratingValues.entrySet()
                .stream()
                .sorted((a, b) -> ascending ?
                        a.getValue().compareTo(b.getValue()) :
                        b.getValue().compareTo(a.getValue()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }

    // _________________________________________________________________________________________________________

    public List<Movie> sortMoviesByActor(Integer actorId) {

        // Pre-validation
        if (actorId == null || actorId <= 0L) {
            throw new IllegalArgumentException("Actor ID must be a positive number and cannot be null.");
        }

        // Validation + Lambda sort on movie & cast
        return movieList.stream()
                .filter(movie -> movie.getMovieInfo() != null && movie.getMovieInfo().getCasts() != null)
                .filter(movie -> movie.getMovieInfo().getCasts().stream()
                .anyMatch(cast -> cast.getId().equals(actorId)))
                .toList();
    }

    // _________________________________________________________________________________________________________

    public List<Movie> sortMoviesByCrew(Integer crewId) {
        // Pre-validation
        if (crewId == null || crewId <= 0L) {
            throw new IllegalArgumentException("Crew ID must be a positive number and cannot be null.");
        }

        // Validation + Lambda filter på movie & crew
        return movieList.stream()
                .filter(movie -> movie.getMovieInfo() != null && movie.getMovieInfo().getCrews() != null)
                .filter(movie -> movie.getMovieInfo().getCrews().stream()
                .anyMatch(crew -> crew.getId().equals(crewId)))
                .toList();
    }

    // _________________________________________________________________________________________________________

    public List<Movie> getMoviesByDirector(Integer crewId) {
        // Pre-validation
        if (crewId == null || crewId <= 0L) {
            throw new IllegalArgumentException("Crew ID must be a positive number.");
        }

        // Only DIRECTORs using our Enum class.
        return movieList.stream()
                .filter(movie -> movie.getMovieInfo() != null && movie.getMovieInfo().getCrews() != null)
                .filter(movie -> movie.getMovieInfo().getCrews().stream()
                .anyMatch(crew -> crew.getId().equals(crewId) &&
                    crew.getJob().equalsIgnoreCase(CreditTitleEnum.DIRECTOR.name())))
                .toList();
    }

    // _________________________________________________________________________________________________________

    public void syncDanishMoviesFromApi(Integer years) {
        MoviePageTMDBDTO firstPage = movieTMDBService.getDanishMoviesByRelease(years, 1).join();

        if (firstPage == null || firstPage.getResults() == null) {
            System.out.println("Ingen film fundet fra TMDB.");
            return;
        }

        List<MovieTMDBDTO> allMovies = new ArrayList<>(firstPage.getResults());
        int totalPages = firstPage.getTotalPages() != null ? firstPage.getTotalPages() : 1;
        for (int page = 2; page <= totalPages; page++) {
            try {
                MoviePageTMDBDTO pageDTO = movieTMDBService.getDanishMoviesByRelease(years, page).join();
                if (pageDTO != null && pageDTO.getResults() != null) {
                    allMovies.addAll(pageDTO.getResults());
                }
            } catch (Exception ex) {
                System.err.println("Fejl under hentning af side " + page + ": " + ex.getMessage());
            }
        }

        syncWithTmdb(allMovies);

        System.out.println("Sync gennemført for de sidste " + years + " år. Antal film: " + allMovies.size());
    }

    // _________________________________________________________________________________________________________

    public void syncWithTmdb(List<MovieTMDBDTO> apiDtos) {

        List<Movie> apiMovies = movieConverter.toEntityList(apiDtos);

        List<Integer> apiIds = new ArrayList<>();
        for (Movie m : apiMovies) {
            apiIds.add(m.getId());
        }

        List<Movie> toRemove = new ArrayList<>();
        for (Movie dbMovie : movieList) {
            if (!apiIds.contains(dbMovie.getId())) {
                toRemove.add(dbMovie);
            }
        }

        for (Movie m : toRemove) {
            movieDAO.delete(m);
            System.out.println("Slettet film: " + m.getMovieInfo().getTitle());
        }

        List<Movie> toAdd = new ArrayList<>();

        for (Movie apiMovie : apiMovies) {
            boolean existsInDb = false;
            for (Movie dbMovie : movieList) {
                if (dbMovie.getId().equals(apiMovie.getId())) {
                    existsInDb = true;
                    break;
                }
            }
            if (!existsInDb) {
                toAdd.add(apiMovie);
            }
        }

        for (Movie m : toAdd) {
            movieDAO.update(m);
            System.out.println("Tilføjet film: " + m.getMovieInfo().getTitle());
        }

        // Hardcoded. Couldn't care less. movies_companies table in DB.
        // Illustration of how it should work. Don't have time to fix it.
        Movie movieCompanyFix = movieDAO.getById(980026);
        Company companyFix = companyService.getById(76);
        movieCompanyFix.setCompanies(new ArrayList<>(List.of(companyFix)));
        if (companyFix.getMovies() == null) {
            companyFix.setMovies(new ArrayList<>());
        }
        companyFix.getMovies().add(movieCompanyFix);
        movieDAO.update(movieCompanyFix);

        refreshMovieCache();
    }

    // _________________________________________________________________________________________________________

    private void refreshMovieCache() {
        this.movieList.clear();
        this.movieList.addAll(movieDAO.getAll());
    }

    // _________________________________________________________________________________________________________

    public List<Movie> getAllMoviesFromDB() {
        return movieDAO.getAll();
    }

    // _________________________________________________________________________________________________________

    public List<Movie> getAllDanishMovies(){
        return movieDAO.getAllDanishMovies(LanguageEnum.DENMARK.getIso639());
    }


}