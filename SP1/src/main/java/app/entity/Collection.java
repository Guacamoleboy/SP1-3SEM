package app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="collections")
public class Collection {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @ManyToMany
    @JoinTable(name = "collection_genres",                                     // Creating new DB Table
            joinColumns = @JoinColumn(name = "collection_id"),                 // Here
            inverseJoinColumns = @JoinColumn(name = "genre_id")                // There
    )
    private List<Genre> genres;

    @Column(name = "name", unique = true)
    private String name;

}