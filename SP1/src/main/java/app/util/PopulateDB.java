package app.util;

import app.config.HibernateConfig;
import app.dao.LanguageDAO;
import app.dao.RoleDAO;
import app.entity.Language;
import app.entity.Role;
import app.enums.LanguageEnum;
import app.enums.RoleEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class PopulateDB {

    // Attributes
    private static boolean rolesPopulated = false;
    private static boolean languagesPopulated = false;

    // _________________________________________________________________

    public static void populateRoles() {
        if (rolesPopulated) {
            System.out.println("PopulateDB Roles: Already populated. Skipping...");
            return;
        }
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            populateRoles(em);
            em.getTransaction().commit();
            rolesPopulated = true;
        } catch (Exception e) {
            System.err.println("Error during role population: " + e.getMessage());
        }
    }

    // _________________________________________________________________

    public static void populateRoles(EntityManager em) {
        RoleDAO roleDAO = new RoleDAO(em);
        if (!roleDAO.getAll().isEmpty()) {
            System.out.println("PopulateDB: Roles already exist in DB. Skipping...");
            return;
        }
        for (RoleEnum roleEnum : RoleEnum.values()) {
            Role role = new Role();
            role.setRoleEnum(roleEnum);
            roleDAO.create(role);
            System.out.println("Role added: " + roleEnum.name());
        }
        System.out.println("PopulateDB: Roles added.");
    }

    // _________________________________________________________________

    public static void populateLanguages() {
        if (languagesPopulated) {
            System.out.println("PopulateDB Languages: Already populated. Skipping...");
            return;
        }
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            populateLanguages(em);
            em.getTransaction().commit();
            languagesPopulated = true;
        }
    }

    // _________________________________________________________________

    public static void populateLanguages(EntityManager em) {
        LanguageDAO languageDAO = new LanguageDAO(em);
        if (!languageDAO.getAll().isEmpty()) {
            System.out.println("PopulateDB: Languages already exist. Skipping...");
            return;
        }
        for (LanguageEnum languageEnum : LanguageEnum.values()) {
            Language language = new Language();
            language.setLanguageEnum(languageEnum);
            System.out.println("Language added: "+ languageEnum.getDisplayName());
            languageDAO.create(language);
        }
        System.out.println("PopulateDB: Languages added.");
    }

}