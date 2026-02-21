package app.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.util.Properties;

public class HibernateConfig {

    // Attributes
    private static EntityManagerFactory emf;
    private static EntityManagerFactory emfTest;

    // __________________________________________________________

    public static void setTest(Boolean test) {
        HibernateEnvironment.setTest(test);
    }

    // __________________________________________________________

    public static Boolean getTest() {
        return HibernateEnvironment.getTest();
    }

    // __________________________________________________________

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null)
            emf = createEMF(getTest());
        return emf;
    }

    // __________________________________________________________

    public static EntityManagerFactory getEntityManagerFactoryForTest() {
        if (emfTest == null){
            setTest(true);
            emfTest = createEMF(getTest());
        }
        return emfTest;
    }

    // __________________________________________________________

    private static EntityManagerFactory createEMF(boolean forTest) {
        try {
            Configuration configuration = new Configuration();
            Properties props = HibernateProperties.setBaseProperties();

            if (forTest) {
                props = HibernateProperties.setTestProperties(props);
            } else if (System.getenv("DEPLOYED") != null) {
                HibernateProperties.setDeployedProperties(props);
            } else {
                props = HibernateProperties.setDevProperties(props);
            }

            configuration.setProperties(props);
            HibernateAnnotation.registerEntities(configuration);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
            return sf.unwrap(EntityManagerFactory.class);

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

}