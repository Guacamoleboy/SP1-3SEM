package app.entity;

import app.enums.LanguageEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
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

    @Column(name = "backdrop_path", unique = true)
    private String backdropPath;

    @Enumerated(EnumType.STRING)
    @Column(name = "original_language")
    private LanguageEnum originalLanguage;

    @Column(name = "original_title")
    private String originalTitle;

    @Column(name = "title")
    private String title;

    @Column(name = "overview")
    private String overview;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "budget")
    private Integer budget;

    @Column(name = "tmdb_id", unique = true, nullable = false)
    private Long tmdbId;

    @Column(name = "run_time")
    private Integer runTime;

    // _________________________________________________________

}