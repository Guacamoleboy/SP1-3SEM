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
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "logo_path", unique = true)
    private String logoPath;

    @Column(name ="name", unique = true)
    private String name;

    @Column(name="original_country")
    private String originalCountry;

    @Column(name = "last_uploaded_movie")
    private LocalDate lastUploadedMovie;

    // __________________________________________________________

    @ManyToMany(mappedBy = "companies")
    private List<Movie> movies;

    // __________________________________________________________
    
}