package app.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import app.entity.User;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserResponseDTO {

    // Outbound

    // Expected JSON format from backend:
    // ___________________
    //
    //      {
    //          "user_id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    //          "username": "Jonaslarsen_",
    //          "email": "jonas68@live.dk",
    //          "role_name": "ADMIN",
    //          "role_description": "Full system access. Can modify, delete, and manage all data and users.",
    //          "company_id": "Jonas A/S",
    //          "company_id": "2",
    //          "created_at": "2026-02-24T12:34:56",
    //          "last_login": "2026-02-24T15:00:00"
    //      }
    //
    // ___________________
    // TESTED: NO
    // BY: N/A

    @JsonProperty("user_id")
    private UUID id;
    private String username;
    private String email;
    @JsonProperty("role_name")
    private String roleName;
    @JsonProperty("role_description")
    private String roleDescription;
    @JsonProperty("company_id")
    private Long companyId;
    @JsonProperty("company_name")
    private String companyName;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("last_login")
    private LocalDateTime lastLogin;

    // ____________________________________________________________

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmailHash();
        this.roleName = user.getRole().getRoleEnum().name();
        this.roleDescription = user.getRole().getRoleEnum().getDescription();
        if (user.getCompany() != null) {
            this.companyId = user.getCompany().getId();
            this.companyName = user.getCompany().getName();
        }
        this.createdAt = user.getCreatedAt();
        this.lastLogin = user.getLastLogin();
    }

    // ____________________________________________________________

    // Usage in Service:
    // ______
    // UserResponseDTO userResponseDTO = new UserResponseDTO(user);

    // ____________________________________________________________

}