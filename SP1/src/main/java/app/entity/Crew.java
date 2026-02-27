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
@Table(name="crews")
public class Crew {

    // Crew.. Anything but the actor.

    // Many (Crew) One (Movie)
    // Not using enum anymore as department does the same from JSON.
    // Can be used to sort later if needed (enum).

    // 1 -> Female | 2 -> Male

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    private Integer gender;
    private String department;
    private String job;
    private String name;
    @Column(name = "credit_id")
    private String creditId;
    @ManyToOne
    @JoinColumn(name="movie_id", nullable = false)
    private Movie movie;

}