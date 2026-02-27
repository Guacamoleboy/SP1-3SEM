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

    // _____________________________________________________________________________________________
    //
    // • Apparently multiple companies own a Movie!
    //
    // • Many (Movie) Many (Genre)
    //          - Bidirectional
    //          - Movie knows Genre. Genre knows Movie.
    //
    // • One (Movie) One (MovieInfo)
    //          - Unidirectional
    //          - Movie knows MovieInfo. MovieInfo doesn't know Movie
    //
    // • One (Movie) One (Collection)
    //          - Bidirectional
    //          - Movie knows Collection. Collection knows Movie.
    //
    // • One (Movie) One (Rating)
    //          - Unidirectional
    //          - Movie knows Rating. Rating doesn't know Movie.
    //
    // • Many (Movie) Many (Company)
    //          - Bidirectional
    //          - Movie knows Company. Company knows Movie.
    //
    // _____________________________________________________________________________________________

    // Columns
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

    // _____________________________________________________________________________________________
    // • Joint Tables (PK on PK)
    //      - Here
    //      - There

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

    // _____________________________________________________________________________________________

}