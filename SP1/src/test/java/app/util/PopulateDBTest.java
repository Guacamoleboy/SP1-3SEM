package app.util;

import app.ASetup;
import app.dao.LanguageDAO;
import app.dao.RoleDAO;
import app.entity.Language;
import app.entity.Role;
import app.enums.LanguageEnum;
import app.enums.RoleEnum;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PopulateDBTest extends ASetup {

    // Attributes
    private RoleDAO roleDAO;
    private LanguageDAO languageDAO;

    // __________________________________________________________________________________________

    @BeforeEach
    void setUp() {
        roleDAO = new RoleDAO(em);
        languageDAO = new LanguageDAO(em);
    }

    // __________________________________________________________________________________________

    @Test
    @DisplayName("Verify that ALGERIA is the 3rd entry in the database")
    void shouldVerifyAlgeriaAtPositionThree() {
        // Arrange
        languageDAO.deleteAll();
        // Act
        PopulateDB.populateLanguages(em);
        // Assert
        List<Language> allLanguages = languageDAO.getAll();
        Language algeria = allLanguages.get(2);
        assertNotNull(algeria);
        assertEquals(LanguageEnum.ALGERIA, algeria.getLanguageEnum());
        assertEquals("DZ", algeria.getLanguageEnum().getCountryCode());
        assertEquals("ar", algeria.getLanguageEnum().getIso639());
    }

    // __________________________________________________________________________________________

    @Test
    @DisplayName("Should insert roles in database")
    void shouldInsertRoles() {
        // Arrange
        roleDAO.deleteAll();
        // Act
        PopulateDB.populateRoles(em);
        // Assert
        int expectedSize = RoleEnum.values().length;
        assertEquals(expectedSize, roleDAO.getAll().size());
    }

    // __________________________________________________________________________________________

    @Test
    @DisplayName("Should insert languages in database")
    void shouldInsertLanguages() {
        // Arrange
        languageDAO.deleteAll();
        // Act
        PopulateDB.populateLanguages(em);
        // Assert
        int expectedSize = LanguageEnum.values().length;
        assertEquals(expectedSize, languageDAO.getAll().size());
    }

    // __________________________________________________________________________________________

    @Test
    @DisplayName("Should not duplicate roles if called twice")
    void shouldNotInsertTwiceRole() {
        // Arrange
        roleDAO.deleteAll();
        // Act
        PopulateDB.populateRoles(em);
        PopulateDB.populateRoles(em);
        // Assert
        int expectedSize = RoleEnum.values().length;
        assertEquals(expectedSize, roleDAO.getAll().size());
    }

    // __________________________________________________________________________________________

    @Test
    @DisplayName("Should skip populate if data already exists in DB | Language")
    void shouldSkipIfDataPresentRole() {
        // Arrange
        roleDAO.deleteAll();
        Role role = new Role();
        role.setRoleEnum(RoleEnum.USER);
        roleDAO.create(role);
        // Act
        PopulateDB.populateRoles(em);
        // Assert
        assertEquals(1, roleDAO.getAll().size());
    }

    // __________________________________________________________________________________________

    @Test
    @DisplayName("Should not duplicate language if called twice")
    void shouldNotInsertTwiceLanguage() {
        // Arrange
        languageDAO.deleteAll();
        // Act
        PopulateDB.populateLanguages(em);
        PopulateDB.populateLanguages(em);
        // Assert
        int expectedSize = LanguageEnum.values().length;
        assertEquals(expectedSize, languageDAO.getAll().size());
    }

    // __________________________________________________________________________________________

    @Test
    @DisplayName("Should skip populate if data already exists in DB | Language")
    void shouldSkipIfDataPresentLanguage() {
        // Arrange
        languageDAO.deleteAll();
        Language language = new Language();
        language.setLanguageEnum(LanguageEnum.DENMARK);
        languageDAO.create(language);
        // Act
        PopulateDB.populateLanguages(em);
        // Assert
        assertEquals(1, languageDAO.getAll().size());
    }

    // __________________________________________________________________________________________

    @Test
    @DisplayName("Should only allow valid Enum | Role")
    void shouldOnlyAllowEnumValuesRole() {
        // Arrange
        String invalidRoleName = "SUPER_ADMIN";
        String validRoleName = "USER";
        // Act & Assert VALID
        RoleEnum validRole = RoleEnum.valueOf(validRoleName);
        assertEquals(RoleEnum.USER, validRole);
        // Act & Assert INVALID
        assertThrows(IllegalArgumentException.class, () -> {
            RoleEnum.valueOf(invalidRoleName);
        });
    }

    // __________________________________________________________________________________________

    @Test
    @DisplayName("Should only allow valid Enum | Language")
    void shouldOnlyAllowEnumValuesLanguage() {
        // Arrange
        String invalidLangName = "DANMRAK";
        String validLangName = "ZAMBIA";
        // Act & Assert VALID
        LanguageEnum validLang = LanguageEnum.valueOf(validLangName);
        assertEquals(LanguageEnum.ZAMBIA, validLang);
        // Act & Assert INVALID
        assertThrows(IllegalArgumentException.class, () -> {
            LanguageEnum.valueOf(invalidLangName);
        });
    }

    // __________________________________________________________________________________________

    @Test
    @DisplayName("Should verify that specific roles exist in RoleEnum")
    void shouldCheckSpecificRoles() {
        // Arrange
        RoleEnum user = RoleEnum.USER;
        RoleEnum admin = RoleEnum.ADMIN;
        // Act & Assert for ADMIN
        assertNotNull(admin);
        assertEquals("ADMIN", admin.name());
        assertTrue(admin.getDescription().contains("Full system access"));
        // Act & Assert for USER
        assertEquals("USER", user.name());
        assertEquals("Standard consumer. Can view movies, create watchlists, and provide ratings or reviews.", user.getDescription());
    }

    // __________________________________________________________________________________________

    @Test
    @DisplayName("Should verify that enum has specific values | Language")
    void shouldVerifyEnumValuesLanguage() {
        // Arrange
        LanguageEnum poland = LanguageEnum.POLAND;
        LanguageEnum zimbabwe = LanguageEnum.ZIMBABWE;
        // Act & Assert for POLAND
        assertNotNull(poland);
        assertEquals("PL", poland.getCountryCode());
        assertEquals("pl", poland.getIso639());
        assertEquals("PL", poland.getIso3166());
        assertEquals("POLAND", poland.getDisplayName());
        // Act & Assert for ZIMBABWE
        assertNotNull(zimbabwe);
        assertEquals("ZW", zimbabwe.getCountryCode());
        assertEquals("en", zimbabwe.getIso639());
        assertEquals("ZW", zimbabwe.getIso3166());
        assertEquals("ZIMBABWE", zimbabwe.getDisplayName());
    }

}