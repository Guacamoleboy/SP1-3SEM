package app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="movies")
public class Movie {

    // Many (Movie) Many (Genre)
    // One (Movie) One (MovieInfo)
    // One (Movie) One (Collection)
    // One (Movie) One (Rating)

    @Id
    @Column(name ="id", unique = true)
    private Long id;

    @ManyToMany
    @JoinTable(name = "movies_genres",                                                          // New movie table
        joinColumns = @JoinColumn(name = "movies_id", referencedColumnName = "id"),             // Here
        inverseJoinColumns = @JoinColumn(name = "genres_id", referencedColumnName = "id")       // There
    )
    private List<Genre> genre;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_infos_id", referencedColumnName = "id", nullable = false)
    private MovieInfo movieInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "collections_id", referencedColumnName = "id")
    private Collection collection;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ratings_id", referencedColumnName = "id")
    private Rating rating;

}