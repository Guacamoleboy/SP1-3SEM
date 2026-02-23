package app.service.external;

import app.config.DotEnv;
import app.config.PoolConfig;
import app.dto.external.MovieTMDBDTO;
import app.exception.ApiException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class MovieTMDBService {

    // Attributes
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String BASE_LANGUAGE = "&language=en-US";

    // __________________________________________________________

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

}