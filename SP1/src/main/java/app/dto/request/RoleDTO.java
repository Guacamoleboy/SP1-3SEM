package app.dto.request;

import app.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoleDTO {

    // Inbound

    // Expected JSON format from frontend: || Works as intended? (N/A)
    // ___________________
    //
    //      {
    //          "role_name": "ADMIN"
    //      }
    //
    // ___________________
    // TESTED: NO
    // BY: N/A

    // Attributes
    @JsonProperty("role_name")
    private RoleEnum roleName;

}