package app.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    // Wrapper for
    // ____________
    // https://api.themoviedb.org/3/movie/{movie_id}
    //
    // Docs:
    // https://developer.themoviedb.org/reference/movie-details

    private Long id;

    // @OneToOne relations so no List needed
    private MovieInfoTMDBDTO movieInfo;
    private RatingTMDBDTO rating;

    private List<GenreTMDBDTO> genres;
    private List<Long> collectionIds;
    private List<Long> companyIds;

}