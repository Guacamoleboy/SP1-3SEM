package app.service;

import app.dao.CompanyDAO;
import app.dto.external.CompanyTMDBDTO;
import app.entity.Company;
import app.service.external.CompanyTMDBService;
import jakarta.persistence.EntityManager;

public class CompanyService extends EntityManagerService<Company> {

    // Attributes
    private final CompanyDAO companyDAO;
    private final CompanyTMDBService companyTMDBService;

    // ______________________________________________________________

    public CompanyService(EntityManager em){
        super(new CompanyDAO(em), Company.class);
        this.companyDAO = (CompanyDAO) this.entityManagerDAO;
        this.companyTMDBService = new CompanyTMDBService();
    }

    // ______________________________________________________________

    public Company getById(Integer companyId) {
        if (companyId == null) return null;
        Company dbCheck = companyDAO.getById(companyId);
        if (dbCheck != null) return dbCheck;
        CompanyTMDBDTO companyTMDBDTO = companyTMDBService.getCompanyById(companyId).join();
        if (companyTMDBDTO == null) return null;

        // Create
        Company company = new Company(companyTMDBDTO);
        return companyDAO.create(company);
    }

}
