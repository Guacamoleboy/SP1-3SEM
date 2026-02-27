package app.entity;

import app.dto.external.CrewTMDBDTO;
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

    // _____________________________________________________________________________________________
    //
    // • Remaining crew when Actors are sorted away from MovieCreditsTMDBDTO
    //
    // • Many (Crew) One (Movie)
    //      - Unidirectional
    //      - Crew knows Movie. Movie doesn't know Crew.
    //      - FK Column
    //      - "movie_id" is "movies.id"
    //
    // • Gender
    //      - 1 is Female
    //      - 2 is Male
    //
    // _____________________________________________________________________________________________

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

    // _____________________________________________________________________________________________

    public Crew(CrewTMDBDTO dto, Movie movie) {
        if(dto == null || movie == null) {
            throw new IllegalArgumentException("DTO and Movie cannot be null");
        }
        this.id = dto.getId();
        this.name = dto.getName();
        this.department = dto.getDepartment();
        this.job = dto.getJob();
        this.gender = dto.getGender();
        this.creditId = dto.getCreditId();
        this.movie = movie;
    }

    // _____________________________________________________________________________________________

}