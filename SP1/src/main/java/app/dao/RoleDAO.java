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

    // Part of IDAO Interface. Implements these by default:
    // ___________________
    //
    // create() | update() | getById() | getColumnById() | updateColumnById()
    // existByColumn() | findEntityByColumn() | getAll() | delete() | deletebyId | deleteAll()

    // _______________________________________________________________


}