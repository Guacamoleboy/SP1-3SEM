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

        // already populated? runtime check
        if (rolesPopulated) {
            System.out.println("PopulateDB: Already populated. Skipping step.");
            return;
        }

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            RoleDAO roleDAO = new RoleDAO(em);

            // Check if Role is empty. If it's empty -> populate.
            boolean hasAnyRole = !roleDAO.getAll().isEmpty();
            if (hasAnyRole) {
                System.out.println("PopulateDB: Roles already exist in DB. Skipping populate.");
            } else {
                for (RoleEnum roleEnum : RoleEnum.values()) {
                    Role role = new Role();
                    role.setRoleEnum(roleEnum);
                    roleDAO.create(role);
                    System.out.println("Inserted role: " + roleEnum.name());
                }
                System.out.println("PopulateDB: Roles inserted successfully.");
            }

            em.getTransaction().commit();
            rolesPopulated = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // _________________________________________________________________

    public static void populateLanguages() {

        if (languagesPopulated) {
            System.out.println("PopulateDB: Already populated. Skipping step.");
            return;
        }

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            LanguageDAO languageDAO = new LanguageDAO(em);

            boolean hasAnyLanguage = !languageDAO.getAll().isEmpty();
            if (hasAnyLanguage) {
                System.out.println("PopulateDB: Languages already exist in DB. Skipping populate.");
            } else {
                for (LanguageEnum langEnum : LanguageEnum.values()) {
                    Language language = new Language();
                    language.setLanguageEnum(langEnum);
                    languageDAO.create(language);
                    System.out.println("Inserted language: " + langEnum.getDisplayName()
                            + " (" + langEnum.getIso639() + "/" + langEnum.getIso3166() + ")");
                }
                System.out.println("PopulateDB: Languages inserted successfully.");
            }

            em.getTransaction().commit();
            languagesPopulated = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}