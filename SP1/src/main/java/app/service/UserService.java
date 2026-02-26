package app.service;

import app.dao.CompanyDAO;
import app.dao.RoleDAO;
import app.dao.UserDAO;
import app.dto.request.UserDTO;
import app.dto.response.UserResponseDTO;
import app.entity.Company;
import app.entity.Role;
import app.entity.User;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import static app.util.BCryptHash.hash;

public class UserService extends EntityManagerService<User> {

    // Attributes
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final CompanyDAO companyDAO;

    // _________________________________________________________
    // Initial super constructor + type cast

    public UserService(EntityManager em){
        super(new UserDAO(em), User.class);
        this.userDAO = (UserDAO) this.entityManagerDAO;
        this.roleDAO = new RoleDAO(em);
        this.companyDAO = new CompanyDAO(em);
    }

    // _________________________________________________________
    // Inbound (UserDTO)

    public void createFromDTO(UserDTO userDTO) {

        // Role Setup
        Role role = roleDAO.findEntityByColumn(userDTO.getRoleId(), "id");

        // User Setup
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmailHash(hash(userDTO.getEmail()));
        user.setPasswordHash(hash(userDTO.getPassword()));
        user.setRole(role);

        // Create
        userDAO.create(user);

    }

    // _________________________________________________________
    // Outbound (UserResponseDTO)

    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(user);
    }

    // _________________________________________________________

    public User findByEmail(String emailHash) {
        return findEntityByColumn(emailHash, "emailHash");
    }

    // _________________________________________________________

    public User findByUsername(String username) {
        return findEntityByColumn(username, "username");
    }

}