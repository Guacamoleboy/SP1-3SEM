package app.util;

import app.config.PoolConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class AsyncHttpClientHelper {


    // PoolConfig -> HttpClient client. Use that.

    // Attributes

    // ___________________________________________________________________
    // TODO: Don't use untill Try-catch has been added. No exception handle as of right now

    /*
    public static CompletableFuture<T> get(String url) {

        // Request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        // ASync Query
        return PoolConfig.getClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() != 200) {
                        throw new RuntimeException("Request failed: " + response.statusCode());
                    }
                    return response.body();
                });
    }

     */
}