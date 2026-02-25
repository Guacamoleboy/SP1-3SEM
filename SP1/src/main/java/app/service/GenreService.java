package app.service;

import app.dao.GenreDAO;
import app.dto.external.GenreTMDBDTO;
import app.entity.Genre;
import app.service.external.GenreTMDBService;
import jakarta.persistence.EntityManager;
import java.util.List;

public class GenreService extends EntityManagerService<Genre> {

    private final GenreDAO genreDAO;
    private final GenreTMDBService genreTMDBService;

    public GenreService(EntityManager em) {
        super(new GenreDAO(em), Genre.class);
        this.genreDAO = (GenreDAO) this.entityManagerDAO;
        this.genreTMDBService = new GenreTMDBService();
    }

    public void persistAllFromTMDB() {
        List<GenreTMDBDTO> genreDTOs = genreTMDBService.getAllGenres().join();
        for (GenreTMDBDTO dto : genreDTOs) {
            if (!existByColumn(dto.getId(), "id")) {
                Genre genre = Genre.builder()
                        .id(dto.getId())
                        .genreName(dto.getName())
                        .build();
                genreDAO.create(genre);
            }
        }
        System.out.println("Stored " + genreDTOs.size() + " genres.");
    }

}