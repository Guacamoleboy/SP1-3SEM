package app.dao;

import app.entity.Company;
import jakarta.persistence.EntityManager;

public class CompanyDAO extends EntityManagerDAO<Company> {

    // Attributes

    // _______________________________________________________________

    public CompanyDAO(EntityManager em) {
        super(em, Company.class);
    }

    // _______________________________________________________________


}