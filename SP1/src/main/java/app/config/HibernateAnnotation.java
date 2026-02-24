package app.config;

import app.entity.*;
import org.hibernate.cfg.Configuration;

public class HibernateAnnotation {

    // Attributes

    // ______________________________________________________________________

    public static void registerEntities(Configuration configuration) {
        //configuration.addAnnotatedClass(Collection.class);
        //configuration.addAnnotatedClass(Company.class);
        //configuration.addAnnotatedClass(Cast.class);
        //configuration.addAnnotatedClass(Crew.class);
        //configuration.addAnnotatedClass(Genre.class);
        configuration.addAnnotatedClass(Language.class);
        //configuration.addAnnotatedClass(Movie.class);
        //configuration.addAnnotatedClass(MovieInfo.class);
        //configuration.addAnnotatedClass(Rating.class);
        configuration.addAnnotatedClass(Role.class);
        //configuration.addAnnotatedClass(Tagline.class);
        //configuration.addAnnotatedClass(User.class);
    }

}