package app.dao;

import app.entity.Language;
import jakarta.persistence.EntityManager;

public class LanguageDAO extends EntityManagerDAO<Language> {

    // Attributes

    // _______________________________________________________________

    public LanguageDAO(EntityManager em) {
        super(em, Language.class);
    }

    // _______________________________________________________________

    // Part of IDAO Interface. Implements these by default:
    // ___________________
    //
    // create() | update() | getById() | getColumnById() | updateColumnById()
    // existByColumn() | findEntityByColumn() | getAll() | delete() | deletebyId | deleteAll()

    // _______________________________________________________________


}