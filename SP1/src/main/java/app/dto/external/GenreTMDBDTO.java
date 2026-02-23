package app.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenreTMDBDTO {

    // Endpoint for
    // ____________
    // https://api.themoviedb.org/3/genre/movie/list
    //
    // Docs:
    // https://developer.themoviedb.org/reference/genre-movie-list

    private Long id;
    private String name;

}