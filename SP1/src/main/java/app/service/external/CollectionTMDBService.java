package app.service.external;

import app.config.DotEnv;
import app.config.PoolConfig;
import app.dto.external.CollectionTMDBDTO;
import app.exception.ApiException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class CollectionTMDBService {

    // Attributes
    private static final String BASE_URL = "https://api.themoviedb.org/3/collection/";
    private static final String BASE_LANGUAGE = "&language=en-US";

    // __________________________________________________________

    public CompletableFuture<CollectionTMDBDTO> getCollectionById(Long collectionId) {

        // Pre-validation
        if (collectionId == null) {
            return CompletableFuture.failedFuture(
                    new ApiException("collectionId can't be null", this.getClass().getName())
            );
        }

        // URL Setup
        String finalUrl = BASE_URL + collectionId + "?api_key=" + DotEnv.getTmdbKey() + BASE_LANGUAGE;

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

                        // 200 validation
                        if (response.statusCode() != 200) {
                            throw new ApiException(response.statusCode(), "Failed to fetch collection " + collectionId, this.getClass().getName());
                        }

                        // Map to CollectionDTO
                        try {
                            return PoolConfig.getMapper().readValue(response.body(), CollectionTMDBDTO.class);
                        } catch (Exception e) {
                            throw new ApiException("Failed to parse CollectionTMDBDTO", e, this.getClass().getName());
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