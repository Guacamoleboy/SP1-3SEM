package app.controller;

import app.entity.Genre;
import app.service.GenreService;
import app.service.external.GenreTMDBService;
import app.service.sync.GenreSyncService;
import jakarta.persistence.EntityManager;
import java.util.Map;

public class GenreController {

    // Attributes
    private final GenreService genreService;
    private final GenreTMDBService genreTMDBService;
    private final GenreSyncService genreSyncService;

    // __________________________________________________________

    public GenreController(EntityManager em) {
        this.genreService = new GenreService(em);
        this.genreTMDBService = new GenreTMDBService();
        this.genreSyncService = new GenreSyncService(this.genreService);
    }

    // __________________________________________________________

    public void downloadGenresFromTMDB() {
        genreService.getGenresFromTMDB();
        System.out.println("Genre sync completed.");
    }

    // __________________________________________________________

    public Map<Integer, Genre> getAllGenres() {
        return genreSyncService.getAllGenres();
    }

    // __________________________________________________________

    public Genre getGenreById(Integer id) {
        return genreSyncService.getById(id);
    }

}