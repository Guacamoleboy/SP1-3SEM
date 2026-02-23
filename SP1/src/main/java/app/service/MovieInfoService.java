package app.service;

import app.dao.MovieInfoDAO;
import app.entity.MovieInfo;
import jakarta.persistence.EntityManager;

public class MovieInfoService extends EntityManagerService<MovieInfo> {

    // Attributes
    private final MovieInfoDAO movieInfoDAO;

    // _________________________________________________________
    // Initial super constructor + type cast

    public MovieInfoService(EntityManager em){
        super(new MovieInfoDAO(em), MovieInfo.class);
        this.movieInfoDAO = (MovieInfoDAO) this.entityManagerDAO;
    }



}