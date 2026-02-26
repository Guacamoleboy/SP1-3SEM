package app.dao;

import app.entity.Rating;
import jakarta.persistence.EntityManager;

public class RatingDAO extends EntityManagerDAO<Rating> {

    // Attributes

    // _______________________________________________________________

    public RatingDAO(EntityManager em) {
        super(em, Rating.class);
    }

    // _______________________________________________________________

    // Part of IDAO Interface. Implements these by default:
    // ___________________
    //
    // create() | update() | getById() | getColumnById() | updateColumnById()
    // existByColumn() | findEntityByColumn() | getAll() | delete() | deletebyId | deleteAll()

    // _______________________________________________________________

}