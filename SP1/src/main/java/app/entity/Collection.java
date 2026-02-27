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
@Table(name="collections")
public class Collection {

    // _____________________________________________________________________________________________
    //
    //  • Many (Collection) Many (Genre)
    //      - Bidirectional
    //      - Collection knows Genre. Genre knows Collection.
    //
    //  • One (Collection) Many (Movie)
    //      - Bidirectional
    //      - Collection knows Movie. Movie knows Collection.
    //      - Mapped relation. Not a column in Collection.
    //
    // _____________________________________________________________________________________________

    // Columns
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "poster_path")
    private String posterPath;
    @Column(name = "backdrop_path")
    private String backdropPath;
    @Column(name = "name", unique = true)
    private String name;

    // _____________________________________________________________________________________________
    // • Joint Tables (PK on PK)
    //      - Here
    //      - There

    @ManyToMany
    @JoinTable(name = "collection_genres",
    joinColumns = @JoinColumn(name = "collection_id"),
    inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    // _____________________________________________________________________________________________

    @OneToMany(mappedBy = "collection")
    private List<Movie> movies;

    // _____________________________________________________________________________________________

}