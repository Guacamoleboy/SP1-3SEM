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

    // Inbound - One Singular Movie

    // Wrapper for
    // ____________
    // https://api.themoviedb.org/3/movie/{movie_id}
    //
    // Docs:
    // https://developer.themoviedb.org/reference/movie-details

    // Expected JSON format from frontend:
    // ___________________
    //
    //      {
    //          "adult": false,
    //          "backdrop_path": "/fkowakowkaofkfokawf.jpg",
    //          "belongs_to_collection": null or [],
    //          "budget": 0,
    //          "genres": [
    //              {
    //              "id": 99,
    //              "name": "Documentary"
    //          ],
    //          "id": 65010101
    //          osv.....
    //      }
    //
    // ___________________
    // TESTED: NO
    // BY: N/A

    private Long id;

    // @OneToOne relations so no List needed
    @JsonUnwrapped
    private MovieInfoTMDBDTO movieInfo;
    @JsonUnwrapped
    private RatingTMDBDTO rating;

    // Genre Object (id, name).
    private List<GenreTMDBDTO> genres;

    // Objects or null
    @JsonProperty("production_companies")
    private List<CompanyTMDBDTO> productionCompanies;
    @JsonProperty("belongs_to_collection")
    private CollectionTMDBDTO collection;
    @JsonProperty("genre_ids")
    private List<Long> genreIds;

}