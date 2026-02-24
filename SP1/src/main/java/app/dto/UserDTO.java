package app.dto;

import app.entity.Company;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import app.entity.Role;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    // Feedback to Frontend (UI)

    // Attributes
    private UUID id;
    private String username;
    private Role role;
    private Company company;
    @JsonProperty("last_login")
    private LocalDateTime lastLogin;

}