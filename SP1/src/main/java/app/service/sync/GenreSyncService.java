package app.service.sync;

import app.entity.Genre;
import app.service.GenreService;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GenreSyncService {

    // Attributes
    private final GenreService genreService;
    private final Map<Long, Genre> genreCache = new ConcurrentHashMap<>();

    // ___________________________________________________________________________

    public GenreSyncService(GenreService genreService) {
        this.genreService = genreService;
    }

    // ___________________________________________________________________________

    private void fillCacheFromDB() {
        if (genreCache.isEmpty()) {
            List<Genre> dbGenres = genreService.getAll();
            for (Genre genre : dbGenres) {
                genreCache.put(genre.getId(), genre);
            }
            System.out.println("\nNo genres found in Cache...\nGenres loaded into cache from DB. Cache full now!");
        }
    }

    // ___________________________________________________________________________

    public Genre getById(Long id) {
        fillCacheFromDB();
        return genreCache.get(id);
    }

    // ___________________________________________________________________________

    public Map<Long, Genre> getAllGenres() {
        fillCacheFromDB();
        return genreCache;
    }

}