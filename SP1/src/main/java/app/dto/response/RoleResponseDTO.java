package app.dto.response;

import app.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoleResponseDTO {

    // Outbound

    // Output JSON format:
    // ___________________
    //
    //      {
    //          "role_name": "ADMIN",
    //          "role_description": "Full system access. Can modify, delete, and manage all data and users."
    //      }
    //
    // ___________________
    // TESTED: NO
    // BY: N/A

    // Attributes
    @JsonProperty("role_name")
    private String name;
    @JsonProperty("role_description")
    private String description;

    // ____________________________________________________________

    public RoleResponseDTO(RoleEnum roleEnum) {
        this.name = roleEnum.name();
        this.description = roleEnum.getDescription();
    }

    // ____________________________________________________________

    // Usage in Service:
    // ______
    // RoleResponseDTO r = new RoleResponseDTO(RoleEnum.ADMIN);

    // ____________________________________________________________

}