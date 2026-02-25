package app.service.converter;

import app.dto.external.MovieTMDBDTO;
import app.entity.*;
import app.enums.LanguageEnum;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MovieConverter {

    // Attributes

    //  __________________________________________________________________________

    public static Movie toEntity(MovieTMDBDTO dto) {

        // Initial validation
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
                        language = null;
                    }
                }
            }

            LocalDate releaseDate = null;
            try {
                if (dto.getMovieInfo().getReleaseDate() != null && !dto.getMovieInfo().getReleaseDate().isEmpty()) {
                    releaseDate = LocalDate.parse(dto.getMovieInfo().getReleaseDate());
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid release date '" + dto.getMovieInfo().getReleaseDate() + "' for movie id " + dto.getId(), e);
            }

            movieInfo = MovieInfo.builder()
                    .tmdbId(dto.getId())
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

        List<Genre> genres = null;
        if (dto.getGenres() != null) {
            genres = dto.getGenres().stream()
                    .map(g -> Genre.builder()
                            .id(g.getId())
                            .genreName(g.getName())
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

    //  __________________________________________________________________________

    public static List<Movie> toEntityList(List<MovieTMDBDTO> dtos) {
        if (dtos == null) return List.of();
        return dtos.stream()
                .map(MovieConverter::toEntity)
                .collect(Collectors.toList());
    }

}