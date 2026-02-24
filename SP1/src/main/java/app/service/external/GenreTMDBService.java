package app.service.external;

import app.config.DotEnv;
import app.config.PoolConfig;
import app.dto.external.GenreResponseTMDBDTO;
import app.dto.external.GenreTMDBDTO;
import app.exception.ApiException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GenreTMDBService {

    // Attributes
    private static final String BASE_URL = "https://api.themoviedb.org/3/genre/movie/list";
    private static final String BASE_LANGUAGE = "&language=en";

    // __________________________________________________________
    // Functionality of Backend (4)
    // ____________________________
    //
    // Store genre for each Movie 

    public CompletableFuture<List<GenreTMDBDTO>> getAllGenres() {

        // No param, so no need to pre-validate! Yay.

        // URL Setup
        String finalUrl = BASE_URL + "?api_key=" + DotEnv.getTmdbKey() + BASE_LANGUAGE;

        try {

            // Request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(finalUrl))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            // ASync call
            return PoolConfig.getClient()
                    .sendAsync(request, BodyHandlers.ofString())
                    .thenApply(response -> {

                        // 200 check
                        if (response.statusCode() != 200) {
                            throw new ApiException(response.statusCode(), "Failed to fetch genres", this.getClass().getName());
                        }

                        // Map to Wrapper then return genres from the List<T> in the class.
                        try {
                            GenreResponseTMDBDTO genreResponse = PoolConfig.getMapper()
                                    .readValue(response.body(), GenreResponseTMDBDTO.class);

                            // List<GenreTMDBDTO> from GenreResponseTMDBDTO as return as that's what we want
                            return genreResponse.getGenres();
                        } catch (Exception e) {
                            throw new ApiException("Failed to parse GenreTMDBDTO list", e, this.getClass().getName());
                        }
                    });

        } catch (Exception e) {
            return CompletableFuture.failedFuture(new ApiException(
                    "Failed to build request",
                    e,
                    this.getClass().getName()
            ));
        }
    }

}