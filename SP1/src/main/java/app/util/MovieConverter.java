package app.util;

import app.dto.external.MovieTMDBDTO;
import app.entity.*;
import app.enums.LanguageEnum;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MovieConverter {

    public static Movie toEntity(MovieTMDBDTO dto) {
        if (dto == null) return null;

        Rating rating = null;
        if (dto.getRating() != null) {
            rating = Rating.builder()
                    .voteAverage(dto.getRating().getVoteAverage())
                    .voteCount(dto.getRating().getVoteCount())
                    .build();
        }

        MovieInfo movieInfo = null;
        if (dto.getMovieInfo() != null) {
            // Parse LanguageEnum safely
            LanguageEnum language = null;
            try {
                if (dto.getMovieInfo().getOriginalLanguage() != null) {
                    language = LanguageEnum.valueOf(dto.getMovieInfo().getOriginalLanguage().toUpperCase());
                }
            } catch (IllegalArgumentException e) {
                // Language not in enum, leave as null
            }

            // Parse LocalDate safely
            LocalDate releaseDate = null;
            try {
                if (dto.getMovieInfo().getReleaseDate() != null && !dto.getMovieInfo().getReleaseDate().isEmpty()) {
                    releaseDate = LocalDate.parse(dto.getMovieInfo().getReleaseDate());
                }
            } catch (Exception e) {
                // Unparseable date, leave as null
            }

            movieInfo = MovieInfo.builder()
                    .tmdbId(dto.getId())                          // MovieInfo uses tmdbId, not id (id is UUID auto-generated)
                    .title(dto.getMovieInfo().getTitle())
                    .originalTitle(dto.getMovieInfo().getOriginalTitle())
                    .overview(dto.getMovieInfo().getOverview())
                    .backdropPath(dto.getMovieInfo().getBackdropPath())  // no posterPath in entity
                    .originalLanguage(language)                   // must be LanguageEnum, not String
                    .releaseDate(releaseDate)                     // must be LocalDate, not String
                    .adult(dto.getMovieInfo().isAdult())
                    .budget(dto.getMovieInfo().getBudget())
                    .runTime(dto.getMovieInfo().getRunTime())
                    .build();
        }

        List<Genre> genres = null;
        if (dto.getGenres() != null) {
            // Full genre objects (from single movie endpoint)
            genres = dto.getGenres().stream()
                    .map(g -> Genre.builder()
                            .id(g.getId())
                            .genreName(g.getName())
                            .build())
                    .collect(Collectors.toList());
        } else if (dto.getGenreIds() != null) {
            // Just IDs (from discover/list endpoint) - build stubs for the controller to swap out
            genres = dto.getGenreIds().stream()
                    .map(id -> Genre.builder()
                            .id(id)
                            .build())
                    .collect(Collectors.toList());
        }

        return Movie.builder()
                .id(dto.getId())
                .movieInfo(movieInfo)
                .rating(rating)
                .genre(genres)
                .build();
    }

    public static List<Movie> toEntityList(List<MovieTMDBDTO> dtos) {
        if (dtos == null) return List.of();
        return dtos.stream()
                .map(MovieConverter::toEntity)
                .collect(Collectors.toList());
    }
}