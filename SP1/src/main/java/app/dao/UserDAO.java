package app.dao;

import app.entity.User;
import jakarta.persistence.EntityManager;

public class UserDAO extends EntityManagerDAO<User> {

    // Attributes

    // _______________________________________________________________

    public UserDAO(EntityManager em) {
        super(em, User.class);
    }

    // _______________________________________________________________


}