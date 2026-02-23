package app.service.external;

import app.config.DotEnv;
import app.config.PoolConfig;
import app.dto.external.CompanyTMDBDTO;
import app.exception.ApiException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class CompanyTMDBService {

    // Attributes
    private static final String BASE_URL = "https://api.themoviedb.org/3/company/";

    // __________________________________________________________

    public CompletableFuture<CompanyTMDBDTO> getCompanyById(Long companyId) {

        // Pre-validation
        if (companyId == null) {
            return CompletableFuture.failedFuture(
                    new ApiException("companyId can't be null", this.getClass().getName())
            );
        }

        // URL Setup
        String finalUrl = BASE_URL + companyId + "?api_key=" + DotEnv.getTmdbKey();

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

                        // 200 Check
                        if (response.statusCode() != 200) {
                            throw new ApiException(response.statusCode(), "Failed to fetch company " + companyId, this.getClass().getName());
                        }

                        // Map to DTO
                        try {
                            return PoolConfig.getMapper().readValue(response.body(), CompanyTMDBDTO.class);
                        } catch (Exception e) {
                            throw new ApiException("Failed to parse CompanyTMDBDTO", e, this.getClass().getName());
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