package app.service.sync;

import app.dto.external.GenreTMDBDTO;
import app.entity.Genre;
import app.service.GenreService;
import app.service.external.GenreTMDBService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class GenreSyncService {

    // TODO: Fix
    // TODO: _______________
    // TODO:
    // TODO: Move all the Genre Sync logic out of GenreController / GenreService and into here.
    // TODO: Store Stuff in HashMaps if needed to reduce API calls (rate limit)

    // Attributes
    private final GenreService genreService;
    private final GenreTMDBService genreTMDBService;
    private Map<Long, Genre> genreCache = new HashMap<>();
    private boolean initialized = false;

    // _________________________________________________________________________

    public GenreSyncService(GenreService genreService, GenreTMDBService genreTMDBService) {
        this.genreService = genreService;
        this.genreTMDBService = genreTMDBService;
    }

    // _________________________________________________________________________

    public synchronized void genreSyncCheck() {

        // If already ran
        if (initialized) {
            return;
        }

        List<Genre> dbGenres = genreService.getAll();

        if (!dbGenres.isEmpty()) {
            for (Genre genre : dbGenres) {
                genreCache.put(genre.getId(), genre);
            }
            initialized = true;
            System.out.println("Genres loaded from DB.");
            return;
        }

        // TMDB list in case DB is empty
        List<GenreTMDBDTO> tmdbGenres;

        try {
            tmdbGenres = genreTMDBService.getAllGenres().get();
        } catch (InterruptedException | ExecutionException e) {

            // Thread safety
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Genre fetch interrupted", e);
            }

            throw new RuntimeException("Failed to fetch genres from TMDB", e);
        }

        for (GenreTMDBDTO dto : tmdbGenres) {
            Genre genre = new Genre();
            genre.setId(dto.getId());
            genre.setGenreName(dto.getName());
            genreService.create(genre);
            genreCache.put(genre.getId(), genre);
        }

        initialized = true;
        System.out.println("Genres fetched from TMDB, stored and cached.");

    }

    // _________________________________________________________________________

    public Genre getById(Long id) {
        return genreCache.get(id);
    }

    // _________________________________________________________________________

    public Map<Long, Genre> getGenreCache() {
        return genreCache;
    }

}