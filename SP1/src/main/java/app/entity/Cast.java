package app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="casts")
public class Cast {

    // _____________________________________________________________________________________________
    //
    // • Actor
    //
    // • Many (Cast) One (Movie)
    //      - Unidirectional
    //      - Cast knows Movie. Movie doesn't know Cast.
    //      - FK Column
    //      - "movie_id" is "movies.id"
    //
    // • Gender
    //      - 1 is Female
    //      - 2 is Male
    //
    // _____________________________________________________________________________________________

    // Columns
    @Id
    private Integer id;
    private String character;
    @Column(name = "cast_order")
    private Integer order;
    private Integer gender;
    private String name;
    @Column(name="cast_id")
    private Integer castId;
    @Column(name="credit_id")
    private String creditId;
    @ManyToOne
    @JoinColumn(name="movie_id", nullable = false)
    private Movie movie;

    // _____________________________________________________________________________________________

}