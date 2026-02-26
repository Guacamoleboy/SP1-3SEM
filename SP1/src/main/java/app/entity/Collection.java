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

    // Object or null. Not boolean as we first thought!
    // UUID removed and swapped for Long (return from API).

    // Attributes
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToMany
    @JoinTable(name = "collection_genres",                                     // Creating new DB Table
            joinColumns = @JoinColumn(name = "collection_id"),                 // Here
            inverseJoinColumns = @JoinColumn(name = "genre_id")                // There
    )
    private List<Genre> genres;

    @Column(name = "poster_path")
    private String posterPath;

    @Column(name = "backdrop_path")
    private String backdropPath;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "collection")
    private List<Movie> movies;

}