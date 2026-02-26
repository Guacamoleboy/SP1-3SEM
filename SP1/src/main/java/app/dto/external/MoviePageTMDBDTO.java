package app.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoviePageTMDBDTO {

    // Inbound - Multiple Movies

    // Wrapper for
    // ____________
    // https://api.themoviedb.org/3/discover/movie?region=DK&with_original_language=da
    //
    // Docs:
    // https://developer.themoviedb.org/reference/discover-movie

    // Expected JSON format from frontend:
    // ___________________
    //
    //      {
    //          "page": 1,
    //          "results": [
    //
    //
    //
    //
    //          ],
    //          "total_pages": 300,
    //          "total_results": 5996
    //      }
    //
    // ___________________
    // TESTED: NO
    // BY: N/A

    private Integer page;
    // List of individual movies where MovieTMDBDTO is 1 movie this wrapper is for multiple entries.
    private List<MovieTMDBDTO> results;
    @JsonProperty("total_pages")
    private Integer totalPages;
    @JsonProperty("total_results")
    private Integer totalResults;

}