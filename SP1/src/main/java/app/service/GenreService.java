package app.service;

import app.dao.GenreDAO;
import app.dto.external.GenreTMDBDTO;
import app.entity.Genre;
import app.service.external.GenreTMDBService;
import jakarta.persistence.EntityManager;
import java.util.List;

public class GenreService extends EntityManagerService<Genre> {

    // TODO: Jonas - 25/02-2026
    // TODO: _________________
    // TODO:
    // TODO: Genre doesn't change often. Should be called once and stored in DB like Role / Language.
    // TODO: Can be ran when needed to check. Should only populate once - maybe move it to PopulatorDB ?

    private final GenreDAO genreDAO;
    private final GenreTMDBService genreTMDBService;

    // ____________________________________________________________

    public GenreService(EntityManager em) {
        super(new GenreDAO(em), Genre.class);
        this.genreDAO = (GenreDAO) this.entityManagerDAO;
        this.genreTMDBService = new GenreTMDBService();
    }

    // ____________________________________________________________

    public void getGenresFromTMDB() {

        List<GenreTMDBDTO> genreDTOs = genreTMDBService.getAllGenres().join();

        if (genreDTOs == null || genreDTOs.isEmpty()) {
            System.out.println("No genres found from TMDB endpoint.");
            return;
        }

        for (GenreTMDBDTO dto : genreDTOs) {
            if (!existByColumn(dto.getId(), "id")) {
                Genre genre = new Genre();
                genre.setId(dto.getId());
                genre.setGenreName(dto.getName());
                create(genre);
                System.out.println("Genre tilføjet: " + dto.getName());
            }
        }

        System.out.println("Genres gemt i DB: " + genreDTOs.size());

    }

}