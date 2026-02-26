package app.service.external;

import app.config.DotEnv;
import app.config.PoolConfig;
import app.dto.external.MoviePageTMDBDTO;
import app.dto.external.MovieTMDBDTO;
import app.exception.ApiException;
import app.service.converter.MovieConverter;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

public class MovieTMDBService {

    // Attributes
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String BASE_LANGUAGE = "&language=en-US";

    // _______________________________________________
    // Get single movie by ID from TMDB

    public CompletableFuture<MovieTMDBDTO> getMovieById(Long movieId) {

        if (movieId == null) {
            return CompletableFuture.failedFuture(
                    new ApiException("movieId can't be null", this.getClass().getName())
            );
        }

        String finalUrl = BASE_URL + movieId + "?api_key=" + DotEnv.getTmdbKey() + BASE_LANGUAGE;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(finalUrl))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            return PoolConfig.getClient()
                    .sendAsync(request, BodyHandlers.ofString())
                    .thenApply(response -> {
                        if (response.statusCode() != 200) {
                            throw new ApiException(response.statusCode(), "Failed to fetch movie " + movieId, this.getClass().getName());
                        }
                        try {
                            return PoolConfig.getMapper().readValue(response.body(), MovieTMDBDTO.class);
                        } catch (Exception e) {
                            throw new ApiException("Failed to parse MovieTMDBDTO", e, this.getClass().getName());
                        }
                    });

        } catch (Exception e) {
            return CompletableFuture.failedFuture(new ApiException(
                    "Failed to build request", e, this.getClass().getName()
            ));
        }
    }

    // _______________________________________________
    // Get all Danish movies from the past X years (paginated)

    private static final String BASE_URL_DISCOVER = "https://api.themoviedb.org/3/discover/movie";


    public CompletableFuture<MoviePageTMDBDTO> getDanishMoviesByRelease(Integer years, int page) {

        LocalDate endDate = LocalDate.now();
        String endDateString = endDate.toString();
        String startDateString = endDate.minusYears(years).toString();

        String finalUrl = BASE_URL_DISCOVER
                + "?api_key=" + DotEnv.getTmdbKey()
                + "&with_original_language=da"
                + "&primary_release_date.gte=" + startDateString
                + "&primary_release_date.lte=" + endDateString
                + "&page=" + page;

        // Debug (Confirmed as working)
        // System.out.println("Fetching: " + finalUrl);

        try {

            // Request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(finalUrl))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            // ASync Query
            return PoolConfig.getClient()
                    .sendAsync(request, BodyHandlers.ofString())
                    .thenApply(response -> {

                        // 200 validation or fail
                        if (response.statusCode() != 200) {
                            throw new ApiException(response.statusCode(), "Failed to fetch danish movies", this.getClass().getName());
                        }
                        try {
                            return PoolConfig.getMapper().readValue(response.body(), MoviePageTMDBDTO.class);
                        } catch (Exception e) {
                            throw new ApiException("Failed to parse MoviePageTMDBDTO", e, this.getClass().getName());
                        }
                    });

        } catch (Exception e) {
            return CompletableFuture.failedFuture(new ApiException(
                    "Failed to build request", e, this.getClass().getName()
            ));
        }

    }

}