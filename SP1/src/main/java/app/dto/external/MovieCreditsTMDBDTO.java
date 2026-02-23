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
public class MovieCreditsTMDBDTO {

    // Wrapper for
    // ____________
    // https://api.themoviedb.org/3/movie/{movie_id}/credits
    //
    // Docs:
    // https://developer.themoviedb.org/reference/movie-credits

    // Attributes
    private Long movieId;
    private List<CastTMDBDTO> cast;
    private List<CrewTMDBDTO> crew;

}