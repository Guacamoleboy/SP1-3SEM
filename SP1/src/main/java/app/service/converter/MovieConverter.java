package app.service.converter;

import app.dto.external.CompanyTMDBDTO;
import app.dto.external.MovieTMDBDTO;
import app.entity.*;
import app.enums.LanguageEnum;
import app.service.CompanyService;
import app.service.sync.GenreSyncService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieConverter {

    // Attributes
    private final CompanyService companyService;
    private final GenreSyncService genreSyncService;

    // ___________________________________________________________________

    public MovieConverter(CompanyService companyService, GenreSyncService genreSyncService) {
        this.companyService = companyService;
        this.genreSyncService = genreSyncService;
    }

    // ___________________________________________________________________

    public Movie toEntity(MovieTMDBDTO dto) {

        if (dto == null) return null;

        // Rating
        Rating rating = null;
        if (dto.getRating() != null) {
            rating = Rating.builder()
                    .voteAverage(dto.getRating().getVoteAverage())
                    .voteCount(dto.getRating().getVoteCount())
                    .build();
        }

        // MovieInfo
        MovieInfo movieInfo = null;
        if (dto.getMovieInfo() != null) {
            LanguageEnum language = null;
            String rawLang = dto.getMovieInfo().getOriginalLanguage();
            if (rawLang != null) {
                if (rawLang.equalsIgnoreCase("da")) {
                    language = LanguageEnum.DENMARK;
                } else {
                    try {
                        language = LanguageEnum.valueOf(rawLang.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Sprog koden '" + rawLang + "' findes ikke i LanguageEnum");
                    }
                }
            }

            LocalDate releaseDate = null;
            if (dto.getMovieInfo().getReleaseDate() != null && !dto.getMovieInfo().getReleaseDate().isEmpty()) {
                try {
                    releaseDate = LocalDate.parse(dto.getMovieInfo().getReleaseDate());
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid release date '" + dto.getMovieInfo().getReleaseDate() + "' for movie id " + dto.getId(), e);
                }
            }

            movieInfo = MovieInfo.builder()
                    .imdbId(dto.getImdbId())
                    .title(dto.getMovieInfo().getTitle())
                    .originalTitle(dto.getMovieInfo().getOriginalTitle())
                    .overview(dto.getMovieInfo().getOverview())
                    .backdropPath(dto.getMovieInfo().getBackdropPath())
                    .originalLanguage(language)
                    .releaseDate(releaseDate)
                    .adult(dto.getMovieInfo().isAdult())
                    .budget(dto.getMovieInfo().getBudget())
                    .runTime(dto.getMovieInfo().getRunTime())
                    .build();
        }

        // Genres
        List<Genre> genres = new ArrayList<>();
        if (dto.getGenreIds() != null) {
            for (Integer genreId : dto.getGenreIds()) {
                Genre genre = this.genreSyncService.getById(genreId);
                if (genre != null) {
                    genres.add(genre);
                }
            }
        }

        // Companies
        List<Company> companies = new ArrayList<>();
        if (dto.getProductionCompanies() != null) {
            for (CompanyTMDBDTO companyDto : dto.getProductionCompanies()) {
                Company company = this.companyService.getById(companyDto.getId());
                if (company != null) {
                    companies.add(company);
                }
            }
        }

        // Builder
        Movie movie =  Movie.builder()
                .id(dto.getId())
                .movieInfo(movieInfo)
                .rating(rating)
                .genre(genres)
                .companies(companies)
                .build();

        // Return
        return movie;

    }

    // __________________________________________________________________________________

    public List<Movie> toEntityList(List<MovieTMDBDTO> movieTMDBDTOList) {
        List<Movie> movies = new ArrayList<>();
        for (MovieTMDBDTO movieTMDBDTO : movieTMDBDTOList) {
            movies.add(toEntity(movieTMDBDTO));
        }
        return movies;
    }

}