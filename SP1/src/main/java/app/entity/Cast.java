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

    // Actor
    // Many (Cast) One (Movie)

    // 1 -> Female | 2 -> Male

    // Attributes
    @Id
    private Long id;
    private String character;

    // "order" is a reserved SQL keyword, renamed to cast_order
    @Column(name="cast_order")
    private Integer order;
    private Integer gender;
    private String name;

    @Column(name="cast_id")
    private Integer castId;

    @Column(name="credit_id")
    private String creditId;

    @ManyToOne
    @JoinColumn(name="movie_id")
    private Movie movie;

}