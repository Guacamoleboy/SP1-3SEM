package app.util;

import app.config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class JpaUtil {

    // Attributes
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    // _______________________________________________________

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // _______________________________________________________

    public static void close() {
        emf.close();
    }

}