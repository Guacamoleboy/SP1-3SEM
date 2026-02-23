package app.service;

import app.dao.CompanyDAO;
import app.entity.Company;
import jakarta.persistence.EntityManager;

public class CompanyService extends EntityManagerService<Company> {

    // Attributes
    private final CompanyDAO companyDAO;

    // _________________________________________________________
    // Initial super constructor + type cast

    public CompanyService(EntityManager em){
        super(new CompanyDAO(em), Company.class);
        this.companyDAO = (CompanyDAO) this.entityManagerDAO;
    }



}