package app.service.external;

import app.config.DotEnv;
import app.config.PoolConfig;
import app.dto.external.MovieTMDBDTO;
import app.exception.ApiException;
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

    // __________________________________________________________
    // Functionality of backend (2)
    // ____________________________
    //
    // Query 20 random movies, and list from DB. No need to query ALL movies from TMDB.

    public CompletableFuture<MovieTMDBDTO> getMovieById(Long movieId) {

        // Pre-validation
        if (movieId == null) {
            return CompletableFuture.failedFuture(
                    new ApiException("movieId can't be null", this.getClass().getName())
            );
        }

        // URL Setup
        String finalUrl = BASE_URL + movieId + "?api_key=" + DotEnv.getTmdbKey() + BASE_LANGUAGE;

        try {

            // Request Setup
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(finalUrl))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            // ASync Query
            return PoolConfig.getClient()
                    .sendAsync(request, BodyHandlers.ofString())
                    .thenApply(response -> {

                        // If no good
                        if (response.statusCode() != 200) {
                            throw new ApiException(response.statusCode(), "Failed to fetch movie " + movieId, this.getClass().getName());
                        }

                        // Map to MovieTMDBDTO
                        try {
                            return PoolConfig.getMapper().readValue(response.body(), MovieTMDBDTO.class);
                        } catch (Exception e) {
                            throw new ApiException("Failed to parse MovieTMDBDTO", e, this.getClass().getName());
                        }
                    });

        } catch (Exception e) {
            // If something breaks -> failedFuture
            return CompletableFuture.failedFuture(new ApiException(
                    "Failed to build request",
                    e,
                    this.getClass().getName()
            ));
        }

    }

    // __________________________________________________________
    // Functionality of backend (1)
    // ____________________________
    //
    // Get all movies from Denmark from the past 5 years, and store in our Database.

    private static final String BASE_URL_FOB_1 = "https://api.themoviedb.org/3/discover/movie";
    private static final String REGION_FOB_1 = "&region=DK";
    private static final String ORIGINAL_LANGUAGE = "&with_origianl_language=da";
    // private static final String RELEASE_DATE_GTE_FOB_1 = "&primarty_release_date_gte=2021-02-24";
    // private static final String RELEASE_DATE_LTE_FOB_1 = "&primary_release_date.lte=2026-02-24";

    public CompletableFuture<MovieTMDBDTO> getDanishMoviesByRelease(Long years) {

        // End Setup
        LocalDate endDateRaw = LocalDate.now();
        String endDateString = endDateRaw.toString();
        String startDate = endDateRaw.minusYears(years).toString();

        // Debug
        System.out.println(endDateRaw + " | " + endDateString + " | " + startDate);

        // URL
        String finalUrl = BASE_URL_FOB_1 + "?api_key=" +DotEnv.getTmdbKey() + REGION_FOB_1 + ORIGINAL_LANGUAGE +
        "&primarty_release_date_gte=" + startDate + "&primary_release_date.lte=" + endDateString;

        // Debug on URL
        System.out.println(finalUrl);

        try {

            // Request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(finalUrl))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            return PoolConfig.getClient()
                    .sendAsync(request, BodyHandlers.ofString())
                    .thenApply(response -> {

                        // 200 Check
                        if (response.statusCode() != 200) {
                            throw new ApiException(response.statusCode(), "Failed to fetch danish movies by year", this.getClass().getName());
                        }

                        // Map to DTO
                        try {
                            return PoolConfig.getMapper().readValue(response.body(), MovieTMDBDTO.class);
                        } catch (Exception e) {
                            throw new ApiException("Failed to parse MovieTMDBDTO", e, this.getClass().getName());
                        }
                    });

        } catch (Exception e) {
            // If something breaks -> failedFuture
            return CompletableFuture.failedFuture(new ApiException(
                    "Failed to build request",
                    e,
                    this.getClass().getName()
            ));
        }
    }

}