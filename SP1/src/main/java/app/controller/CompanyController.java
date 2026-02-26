package app.controller;

import app.dao.CompanyDAO;
import app.entity.Company;
import app.service.CompanyService;
import app.service.external.CompanyTMDBService;
import jakarta.persistence.EntityManager;

public class CompanyController {

    // Attributes
    private final CompanyDAO companyDAO;
    private final CompanyService companyService;
    private final CompanyTMDBService companyTMDBService;

    // ______________________________________________________________________

    public CompanyController(EntityManager em){
        this.companyTMDBService = new CompanyTMDBService();
        this.companyDAO = new CompanyDAO(em);
        this.companyService = new CompanyService(em);
    }

    // ______________________________________________________________________

    public Company getById(Integer companyId){
        return companyService.getById(companyId);
    }

}