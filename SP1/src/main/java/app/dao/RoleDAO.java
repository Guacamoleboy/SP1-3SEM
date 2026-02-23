package app.dao;

import app.entity.Role;
import jakarta.persistence.EntityManager;

public class RoleDAO extends EntityManagerDAO<Role> {

    // Attributes

    // _______________________________________________________________

    public RoleDAO(EntityManager em) {
        super(em, Role.class);
    }

    // _______________________________________________________________


}