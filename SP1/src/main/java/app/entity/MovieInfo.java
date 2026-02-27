package app.entity;

import app.enums.LanguageEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="movie_infos")
public class MovieInfo {

    // _____________________________________________________________________________________________
    //
    //  • LanguageEnum languageEnum takes the first String in the enum
    //      - Which would be DENMARK for example - and store it.
    //
    //  • Many (MovieInfo) Many (Crew)
    //      - Unidirectional
    //      - MovieInfo knows Crew. Crew doesn't know MovieInfo.
    //
    //  • Many (MovieInfo) Many (Cast)
    //      - Unidirectional
    //      - MovieInfo knows Cast. Cast doesn't know MovieInfo.
    //
    // _____________________________________________________________________________________________

    // Columns
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "adult")
    private boolean adult;
    @Column(name = "backdrop_path")
    private String backdropPath;
    @Enumerated(EnumType.STRING)
    @Column(name = "original_language")
    private LanguageEnum originalLanguage;
    @Column(name = "original_title")
    private String originalTitle;
    @Column(name = "title")
    private String title;
    @Column(name = "overview", columnDefinition = "TEXT")
    private String overview;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    @Column(name = "budget")
    private Integer budget;
    @Column(name = "tagline", columnDefinition = "TEXT")
    private String tagline;
    @Column(name = "imdb_id", unique = true)
    private String imdbId;
    @Column(name = "run_time")
    private Integer runTime;

    // _____________________________________________________________________________________________
    // • Joint Tables (PK on PK)
    //      - Here
    //      - There

    @ManyToMany
    @JoinTable(name = "movie_info_crews",
    joinColumns = @JoinColumn(name = "movie_info_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "crew_id", referencedColumnName = "id"))
    List<Crew> crews;

    @ManyToMany
    @JoinTable(name = "movie_info_casts",
    joinColumns = @JoinColumn(name = "movie_info_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "cast_id", referencedColumnName = "id"))
    List<Cast> casts;

    // _____________________________________________________________________________________________

}