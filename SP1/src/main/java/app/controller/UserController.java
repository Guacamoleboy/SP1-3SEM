package app.controller;

import app.dto.request.UserDTO;
import app.entity.Role;
import app.entity.User;
import app.enums.RoleEnum;
import app.service.RoleService;
import app.service.UserService;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserController {

    // Attributes
    private final UserService userService;
    private final RoleService roleService;

    // _______________________________________________________________________________

    public UserController(EntityManager em){
        this.userService = new UserService(em);
        this.roleService = new RoleService(em);
    }

    // _______________________________________________________________________________

    public void createFromDTO(UserDTO userDTO) {
        userService.createFromDTO(userDTO);
    }

    // ______________________________________________________________________________

    public void placeholderUsers() {
        Role companyRole = roleService.findRoleByName(RoleEnum.COMPANY);

        // User 1
        UserDTO user1 = new UserDTO();
        user1.setUsername("JonasLarsen");
        user1.setEmail("jonas68@live.dk");
        user1.setPassword("Password123!");
        user1.setRoleId(companyRole.getId());

        // User 2
        UserDTO user2 = new UserDTO();
        user2.setUsername("AndreasRovelt");
        user2.setEmail("andreas@rovelt.dk");
        user2.setPassword("Password123!");
        user2.setRoleId(companyRole.getId());

        List<UserDTO> users = new ArrayList<>();

        users.add(user1);
        users.add(user2);

        // Persist
        for (UserDTO userDTO : users){
            User existCheck = userService.findEntityByColumn(userDTO.getUsername(), "username");
            if (existCheck != null){
                System.out.println("User already found.. Skipping.");
            } else {
                createFromDTO(userDTO);
                System.out.println("User created: " + userDTO.getUsername());
            }
        }
    }

}