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
@Table(name="genres")
public class Genre {

    // _____________________________________________________________________________________________
    //
    // • Many (Genre) Many (Movie)
    //      - Bidirectional
    //      - Genre knows Movie. Movie knows Genre.
    //      - Mapped relation. Not a column in Genre.
    //
    // _____________________________________________________________________________________________

    // Columns
    @Id
    @Column(name = "id", unique = true)
    private Integer id;
    @Column(name = "name", unique = true)
    private String genreName;

    // _____________________________________________________________________________________________

    @ManyToMany(mappedBy = "genre")
    private List<Movie> movies;

    // _____________________________________________________________________________________________

}