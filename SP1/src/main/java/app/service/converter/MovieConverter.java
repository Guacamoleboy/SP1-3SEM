package app.service.converter;

import app.dto.external.MovieTMDBDTO;
import app.entity.*;
import app.enums.LanguageEnum;
import app.service.sync.GenreSyncService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MovieConverter {

    public static Movie toEntity(MovieTMDBDTO dto, GenreSyncService genreSyncService) {
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

        // Genres – altid en tom liste, aldrig null
        List<Genre> genres = new ArrayList<>();

        if (genreSyncService != null) {
            // Case A: full objects (movie details endpoint)
            if (dto.getGenres() != null && !dto.getGenres().isEmpty()) {
                genres = dto.getGenres().stream()
                        .map(g -> genreSyncService.getById(g.getId()))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }
            // Case B: discover/search endpoints (genre_ids)
            else if (dto.getGenreIds() != null && !dto.getGenreIds().isEmpty()) {
                genres = dto.getGenreIds().stream()
                        .map(genreSyncService::getById)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }
            if (dto.getGenreIds() != null) {
                System.out.println("Movie " + dto.getId() + " genreIds=" + dto.getGenreIds() + " mappedGenres=" + genres.size());
            } else {
                System.out.println("Movie " + dto.getId() + " genreIds=null mappedGenres=" + genres.size());
            }
        }


        return Movie.builder()
                .id(dto.getId())
                .movieInfo(movieInfo)
                .rating(rating)
                .genre(genres) // altid liste, aldrig null
                .build();
    }

    public static List<Movie> toEntityList(List<MovieTMDBDTO> dtos, GenreSyncService genreSyncService) {
        if (dtos == null) return List.of();
        return dtos.stream()
                .map(dto -> toEntity(dto, genreSyncService))
                .collect(Collectors.toList());
    }
}