package app.dto.response;

import app.enums.LanguageEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LanguageResponseDTO {

    // Outbound

    // Expected JSON format from backend:
    // ___________________
    //
    //      {
    //          "language_name": "DENMARK",
    //          "country_code": "DK",
    //          "iso_639_1": "da",
    //          "iso_3166_1": "DK"
    //      }
    //
    // ___________________
    // TESTED: NO
    // BY: N/A

    @JsonProperty("language_name")
    private String name;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("iso_639_1")
    private String iso639;

    @JsonProperty("iso_3166_1")
    private String iso3166;

    // ____________________________________________________________

    public LanguageResponseDTO(LanguageEnum languageEnum) {
        this.name = languageEnum.getDisplayName();
        this.countryCode = languageEnum.getCountryCode();
        this.iso639 = languageEnum.getIso639();
        this.iso3166 = languageEnum.getIso3166();
    }

    // ____________________________________________________________

    // Usage in Service:
    // __________________
    // LanguageResponseDTO languageResponseDTO = new LanguageResponseDTO(LanguageEnum.DENMARK);

}