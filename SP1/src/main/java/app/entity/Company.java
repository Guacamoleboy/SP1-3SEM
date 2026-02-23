package app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="companies")
public class Company {

    // One (Company) One (User)
    // One (Company) Many (Movie)
    
    // Attributes
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id,", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany
    @JoinColumn(name = "movie_id", referencedColumnName = "id", nullable = false)
    private Movie movie;

    @Column(name = "logo_path", unique = true)
    private String logoPath;

    @Column(name ="name", unique = true)
    private String name;

    @Column(name="original_country")
    private String originalCountry;

    @Column(name = "last_uploaded_movie")
    private LocalDate lastUploadedMovie;

    // __________________________________________________________

    private List<Movie> movieList;

    // __________________________________________________________
    
}