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

    // Attributes
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

    // Plot summaries can be long. varchar(255) might not be enough.
    @Column(name = "overview", columnDefinition = "TEXT")
    private String overview;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "budget")
    private Integer budget;

    @Column(name = "tmdb_id", unique = true, nullable = false)
    private Integer tmdbId;

    @Column(name = "run_time")
    private Integer runTime;

    @ManyToMany
    @JoinTable(name = "movie_info_crews",                                                         // New movie table
            joinColumns = @JoinColumn(name = "movie_info_id", referencedColumnName = "id"),       // Here
            inverseJoinColumns = @JoinColumn(name = "crew_id", referencedColumnName = "id")       // There
    )
    List<Crew> crews;

    @ManyToMany
    @JoinTable(name = "movie_info_casts",
            joinColumns = @JoinColumn(name = "movie_info_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "cast_id", referencedColumnName = "id")
    )
    List<Cast> casts;

}