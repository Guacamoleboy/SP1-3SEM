package app.service;

import app.dao.RoleDAO;
import app.entity.Role;
import jakarta.persistence.EntityManager;

public class RoleService extends EntityManagerService<Role> {

    // Attributes
    private final RoleDAO RoleDAO;

    // _________________________________________________________
    // Initial super constructor + type cast

    public RoleService(EntityManager em){
        super(new RoleDAO(em), Role.class);
        this.RoleDAO = (RoleDAO) this.entityManagerDAO;
    }



}