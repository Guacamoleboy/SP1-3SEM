package app;

import app.service.external.MovieTMDBService;

public class Main {

    // Attributes

    // ______________________________________________

    public static void main(String[] args) {

        // Empty right now.
        MovieTMDBService mv = new MovieTMDBService();
        mv.getDanishMoviesByRelease(5L);
    }

}