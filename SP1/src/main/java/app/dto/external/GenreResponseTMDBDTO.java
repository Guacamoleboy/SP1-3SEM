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
public class GenreResponseTMDBDTO {

    // Endpoint for
    // ____________
    // https://api.themoviedb.org/3/genre/movie/list
    //
    // Docs:
    // https://developer.themoviedb.org/reference/genre-movie-list

    private List<GenreTMDBDTO> genres;

}