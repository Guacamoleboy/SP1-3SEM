package app.service;

import app.dao.UserDAO;
import app.entity.User;
import jakarta.persistence.EntityManager;

public class UserService extends EntityManagerService<User> {

    // Attributes
    private final UserDAO UserDAO;

    // _________________________________________________________
    // Initial super constructor + type cast

    public UserService(EntityManager em){
        super(new UserDAO(em), User.class);
        this.UserDAO = (UserDAO) this.entityManagerDAO;
    }



}