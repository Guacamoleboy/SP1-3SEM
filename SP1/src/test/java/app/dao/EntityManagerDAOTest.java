package app.dao;

import app.ASetup;
import app.entity.User;
import app.entity.Role;
import app.enums.RoleEnum;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EntityManagerDAOTest extends ASetup {

    // Usage reference:
    // https://github.com/Guacamoleboy/3-Semester-Friday/blob/main/backend/src/test/java/dk/project/dao/EntityManagerDAOTest.java

    private EntityManagerDAO<User> dao;

    // --------------------------------------------------

    @BeforeEach
    void initDAO() {
        dao = new EntityManagerDAO<>(em, User.class);
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
        dao.create(u);
        assertNotNull(u.getId());
    }

    // --------------------------------------------------

    @Test
    void update() {
        User u = createUser();
        dao.create(u);
        u.setUsername("updated");
        User updated = dao.update(u);
        assertEquals("updated", updated.getUsername());
    }

    // --------------------------------------------------

    @Test
    void delete() {
        User u = createUser();
        dao.create(u);
        dao.delete(u);
        assertNull(dao.getById(u.getId()));
    }

    // --------------------------------------------------

    @Test
    void deleteById() {
        User u = createUser();
        dao.create(u);
        dao.deleteById(u.getId());
        assertNull(dao.getById(u.getId()));
    }

    // --------------------------------------------------

    @Test
    void getById() {
        User u = createUser();
        dao.create(u);
        User found = dao.getById(u.getId());
        assertNotNull(found);
    }

    // --------------------------------------------------

    @Test
    void existByColumn() {
        User u = createUser();
        u.setUsername("exists");
        dao.create(u);
        assertTrue(dao.existByColumn("exists", "username"));
    }

    // --------------------------------------------------

    @Test
    void findEntityByColumn() {
        User u = createUser();
        u.setUsername("find");
        dao.create(u);
        User found = dao.findEntityByColumn("find", "username");
        assertNotNull(found);
        assertEquals("find", found.getUsername());
    }

    // --------------------------------------------------

    @Test
    void getAll() {
        dao.create(createUser());
        dao.create(createUser2());
        List<User> all = dao.getAll();
        assertEquals(2, all.size());
    }

    // --------------------------------------------------

    @Test
    void deleteAll() {
        dao.create(createUser());
        dao.create(createUser2());
        dao.deleteAll();
        assertEquals(0, dao.getAll().size());
    }

    // --------------------------------------------------

    @Test
    void getColumnById() {
        User u = createUser();
        u.setUsername("column");
        dao.create(u);
        String name = dao.getColumnById(u.getId(), "username");
        assertEquals("column", name);
    }

    // --------------------------------------------------

    @Test
    void updateColumnById() {
        User u = createUser();
        u.setUsername("old");
        dao.create(u);
        int updated = dao.updateColumnById(u.getId(), "username", "new");
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