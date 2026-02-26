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

    // DB COLUMNS
    @Id
    @Column(name ="id")
    private Integer id;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_info_id", referencedColumnName = "id", nullable = false)
    private MovieInfo movieInfo;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "collection_id", referencedColumnName = "id")
    private Collection collection;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rating_id", referencedColumnName = "id")
    private Rating rating;

    // NEW TABLES FROM DATA
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "movies_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genre;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "movies_companies",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "company_id"))
    private List<Company> companies;

}