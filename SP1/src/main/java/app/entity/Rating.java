package app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="ratings")
public class Rating {

    // _____________________________________________________________________________________________
    //
    // • Could be placed in MovieInfo but moved to separate table for clarification
    //          - "vote_average"
    //          - "vote_count"
    //
    // _____________________________________________________________________________________________

    // Columns
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;
    @Column(name = "vote_average")
    private Double voteAverage;
    @Column(name = "vote_count")
    private Integer voteCount;

    // _____________________________________________________________________________________________

}