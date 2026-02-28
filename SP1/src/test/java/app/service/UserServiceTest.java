package app.service;

import app.ASetup;
import app.dto.request.UserDTO;
import app.entity.Role;
import app.entity.User;
import app.enums.RoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest extends ASetup {

    // Attributes
    private UserService userService;
    private Role role;

    // ____________________________________________________________________________

    @BeforeEach
    public void setUp() {
        userService = new UserService(em);

        // Role
        role = Role.builder()
                .roleEnum(RoleEnum.USER)
                .build();
        em.persist(role);

        em.flush();

        // UserDTO
        UserDTO userDTO = UserDTO.builder()
                .username("Jonaslarsen_")
                .email("jonas68@live.dk")
                .password("password123!")
                .roleId(role.getId())
                .build();

        // Create & Flush
        userService.createFromDTO(userDTO);
        em.flush();
        em.clear();
    }

    // ____________________________________________________________________________

    @Test
    @DisplayName("Should create user from DTO")
    void shouldCreateUserFromDTO() {
        // Act
        User savedUser = userService.findByUsername("Jonaslarsen_");

        // Assert
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals("Jonaslarsen_", savedUser.getUsername());
        assertNotNull(savedUser.getCreatedAt());
        assertNotNull(savedUser.getLastLogin());
        assertNotEquals("jonas68@live.dk", savedUser.getEmailHash());
        assertTrue(savedUser.getPasswordHash().startsWith("$2a$"));
    }

    // ____________________________________________________________________________

    @Test
    @DisplayName("Should find existing user by emailHash")
    void shouldFindByEmail() {
        // Arrange
        User foundUser = userService.findByUsername("Jonaslarsen_");
        String emailHash = foundUser.getEmailHash();

        // Act
        User foundEmail = userService.findByEmail(emailHash);

        // Assert
        assertNotNull(foundEmail);
        assertEquals("Jonaslarsen_", foundEmail.getUsername());
    }

}