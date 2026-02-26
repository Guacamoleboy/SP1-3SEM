package app;

import app.config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ASETUPTest {

    // Attributes
    protected EntityManagerFactory emf;
    protected EntityManager em;

    // ______________________________________________

    @BeforeAll
    protected void setupAll() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
    }

    // ______________________________________________

    @BeforeEach
    protected void setup() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.clear();
    }

    // ______________________________________________

    @AfterEach
    protected void cleanup() {
        if (em != null && em.isOpen()) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    // ______________________________________________

    @AfterAll
    protected void closeAll() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

}