package app.dao;

import app.entity.User;
import app.entity.Role;
import app.enums.RoleEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntityManagerDAOTest {

    private static PostgreSQLContainer<?> postgres;
    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityManagerDAO<User> dao;

    @BeforeAll
    static void init() {
        postgres = new PostgreSQLContainer<>("postgres:15")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");
        postgres.start();

        System.setProperty("jakarta.persistence.jdbc.url", postgres.getJdbcUrl());
        System.setProperty("jakarta.persistence.jdbc.user", postgres.getUsername());
        System.setProperty("jakarta.persistence.jdbc.password", postgres.getPassword());

        emf = Persistence.createEntityManagerFactory("test-pu");
    }

    // --------------------------------------------------

    @AfterAll
    static void cleanup() {
        if (emf != null) emf.close();
        if (postgres != null) postgres.stop();
    }

    // --------------------------------------------------

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        dao = new EntityManagerDAO<>(em, User.class);

        em.getTransaction().begin();
        em.createQuery("DELETE FROM User").executeUpdate();
        em.createQuery("DELETE FROM Role").executeUpdate();
        em.getTransaction().commit();
    }

    // --------------------------------------------------

    @AfterEach
    void tearDown() {
        em.clear();
    }

    // --------------------------------------------------

    private Role createRole() {
        Role role = new Role();
        role.setRoleEnum(RoleEnum.USER);
        RoleDAO roleDAO = new RoleDAO(em);
        return roleDAO.create(role);
    }

    // --------------------------------------------------

    private User createUser() {
        Role role = createRole();

        User u = new User();
        u.setUsername("testuser");
        u.setRole(role);
        u.setEmailHash("hash");
        u.setPasswordHash("pass");
        return u;
    }

    // --------------------------------------------------

    private User createUser2() {
        Role role = createRole();

        User u = new User();
        u.setUsername("testuser2");
        u.setRole(role);
        u.setEmailHash("hash2");
        u.setPasswordHash("pass2");
        return u;
    }

    // --------------------------------------------------

    @Test
    void create() {
        User u = createUser();

        em.getTransaction().begin();
        dao.create(u);
        em.getTransaction().commit();

        assertNotNull(u.getId());
    }

    // --------------------------------------------------

    @Test
    void update() {
        User u = createUser();

        em.getTransaction().begin();
        dao.create(u);
        em.getTransaction().commit();

        em.getTransaction().begin();
        u.setUsername("updated");
        User updated = dao.update(u);
        em.getTransaction().commit();

        assertEquals("updated", updated.getUsername());
    }

    // --------------------------------------------------

    @Test
    void delete() {
        User u = createUser();

        em.getTransaction().begin();
        dao.create(u);
        em.getTransaction().commit();

        em.getTransaction().begin();
        dao.delete(u);
        em.getTransaction().commit();

        assertNull(dao.getById(u.getId()));
    }

    // --------------------------------------------------

    @Test
    void deleteById() {
        User u = createUser();

        em.getTransaction().begin();
        dao.create(u);
        em.getTransaction().commit();

        em.getTransaction().begin();
        dao.deleteById(u.getId());
        em.getTransaction().commit();

        assertNull(dao.getById(u.getId()));
    }

    // --------------------------------------------------

    @Test
    void getById() {
        User u = createUser();

        em.getTransaction().begin();
        dao.create(u);
        em.getTransaction().commit();

        User found = dao.getById(u.getId());

        assertNotNull(found);
    }

    // --------------------------------------------------

    @Test
    void existByColumn() {
        User u = createUser();
        u.setUsername("exists");

        em.getTransaction().begin();
        dao.create(u);
        em.getTransaction().commit();

        assertTrue(dao.existByColumn("exists", "username"));
    }

    // --------------------------------------------------

    @Test
    void findEntityByColumn() {
        User u = createUser();
        u.setUsername("find");

        em.getTransaction().begin();
        dao.create(u);
        em.getTransaction().commit();

        User found = dao.findEntityByColumn("find", "username");

        assertNotNull(found);
        assertEquals("find", found.getUsername());
    }

    // --------------------------------------------------

    @Test
    void getAll() {
        em.getTransaction().begin();
        dao.create(createUser());
        dao.create(createUser2());
        em.getTransaction().commit();

        List<User> all = dao.getAll();

        assertEquals(2, all.size());
    }

    // --------------------------------------------------

    @Test
    void deleteAll() {
        em.getTransaction().begin();
        dao.create(createUser());
        dao.create(createUser2());
        em.getTransaction().commit();

        em.getTransaction().begin();
        dao.deleteAll();
        em.getTransaction().commit();

        assertEquals(0, dao.getAll().size());
    }

    // --------------------------------------------------

    @Test
    void getColumnById() {
        User u = createUser();
        u.setUsername("column");

        em.getTransaction().begin();
        dao.create(u);
        em.getTransaction().commit();

        String name = dao.getColumnById(u.getId(), "username");

        assertEquals("column", name);
    }

    // --------------------------------------------------

    @Test
    void updateColumnById() {
        User u = createUser();
        u.setUsername("old");

        em.getTransaction().begin();
        dao.create(u);
        em.getTransaction().commit();

        em.getTransaction().begin();
        int updated = dao.updateColumnById(u.getId(), "username", "new");
        em.getTransaction().commit();
        em.clear();
        assertEquals(1, updated);
        assertEquals("new", dao.getById(u.getId()).getUsername());
    }

    // --------------------------------------------------

    @Test
    void executeQuery() {
        String result = dao.executeQuery(() -> "ok");
        assertEquals("ok", result);
    }

    // --------------------------------------------------

    @Test
    void testExecuteQuery() {
        dao.executeQuery(() -> {});
        assertTrue(true);
    }
}