package app.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.UUID;

@Data
public class UserDTO {

    // Inbound

    // Expected JSON format from frontend:
    // ___________________
    //
    //      {
    //          "username": "Jonaslarsen_",
    //          "email": "jonas68@live.dk",
    //          "password": "password123!",
    //          "role_id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    //          "company_id": "1"
    //      }
    //
    // ___________________
    // TESTED: NO
    // BY: N/A

    private String username;
    private String email;
    private String password;
    @JsonProperty("role_id")
    private UUID roleId;
    @JsonProperty("company_id")
    private Integer companyId;

}