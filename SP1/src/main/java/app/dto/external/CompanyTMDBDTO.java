package app.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyTMDBDTO {

    // Endpoint for
    // ____________
    // https://api.themoviedb.org/3/company/{company_id}
    //
    // Docs:
    // https://developer.themoviedb.org/reference/company-details

    private Integer id;
    private String name;
    @JsonProperty("logo_path")
    private String logoPath;
    @JsonProperty("origin_country")
    private String originCountry;
    private String description;
    private String headquarters;
    private String homepage;
    @JsonProperty("parent_company")
    private String parentCompany;

}