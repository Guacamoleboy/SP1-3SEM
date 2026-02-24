package app.dto;

import app.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    // We pretend that we have a Frontend (UI) system that sends a request to our backend.
    // We receive a RESPONSE. Hence the name.

    // Attributes
    private UUID id;
    private String username;
    private Role role;
    @JsonProperty("email_hash")
    private String emailHash;
    @JsonProperty("password_hash")
    private String passwordHash;

}