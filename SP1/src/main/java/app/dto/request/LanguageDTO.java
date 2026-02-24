package app.dto.request;

import app.enums.LanguageEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LanguageDTO {

    // Inbound

    // Expected JSON format from frontend:
    // ___________________
    //
    //      {
    //          "language_enum": "DENMARK"
    //      }
    //
    // ___________________
    // TESTED: NO
    // BY: N/A

    @JsonProperty("language_enum")
    private LanguageEnum languageEnum;

}