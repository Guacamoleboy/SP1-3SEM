package app.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieTMDBDTO {

    // @JsonUnwrapped is used in order to get RatingTMDBDTO objects
    // as vote_xxx is on root level.
    // TODO: NOT TESTED YET! Got to run code in order to check it.

    // Wrapper for
    // ____________
    // https://api.themoviedb.org/3/movie/{movie_id}
    //
    // Docs:
    // https://developer.themoviedb.org/reference/movie-details

    private Long id;

    // @OneToOne relations so no List needed
    @JsonUnwrapped
    private MovieInfoTMDBDTO movieInfo;
    @JsonUnwrapped
    private RatingTMDBDTO rating;
    private List<GenreTMDBDTO> genres;

    // Objects or null
    @JsonProperty("production_companies")
    private List<CompanyTMDBDTO> productionCompanies;
    @JsonProperty("belongs_to_collection")
    private CollectionTMDBDTO collection;

}