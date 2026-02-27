package app.service;

import app.dao.CastDAO;
import app.dto.external.CastTMDBDTO;
import app.entity.Cast;
import app.entity.Movie;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CastService extends EntityManagerService<Cast> {

    // Attributes
    private final CastDAO castDAO;

    // _______________________________________________________________

    public CastService(EntityManager em) {
        super(new CastDAO(em), Cast.class);
        this.castDAO = (CastDAO) this.entityManagerDAO;
    }

    // _______________________________________________________________

    public void saveCastsForMovie(List<CastTMDBDTO> castDTOs, Movie movie) {
        if (castDTOs == null || movie == null) return;
        for (CastTMDBDTO dto : castDTOs) {
            Cast cast = new Cast(dto, movie);
            castDAO.update(cast);
        }
    }

}