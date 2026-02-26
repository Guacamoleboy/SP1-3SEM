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
public class CastTMDBDTO {

    // Attributes
    private Integer id;
    private String name;
    private String character;
    private Integer order;
    private Integer gender;
    @JsonProperty("cast_id")
    private Integer castId;
    @JsonProperty("credit_id")
    private String creditId;

}