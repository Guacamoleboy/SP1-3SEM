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

    // Apparently multiple companies own a Movie. Så ManyToMany here.

    // Many (Movie) Many (Genre)
    // One (Movie) One (MovieInfo)
    // One (Movie) One (Collection)
    // One (Movie) One (Rating)
    // Many (Movie) Many (Company)

    @Id
    @Column(name ="id", unique = true)
    private Long id;

    @ManyToMany
    @JoinTable(name = "movies_genres",                                                         // New movie table
        joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),             // Here
        inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id")       // There
    )
    private List<Genre> genre;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_info_id", referencedColumnName = "id", nullable = false)
    private MovieInfo movieInfo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "collection_id", referencedColumnName = "id")
    private Collection collection;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rating_id", referencedColumnName = "id")
    private Rating rating;

    @ManyToMany
    @JoinTable(name = "movie_companies",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private List<Company> companies;

}