package app.entity;

import app.dto.external.CompanyTMDBDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(name = "logo_path")
    private String logoPath;

    @Column(name ="name", unique = true)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "headquarters")
    private String headquarters;

    @Column(name = "homepage")
    private String homepage;

    @Column(name = "parent_company")
    private String parentCompany;

    @Column(name="original_country")
    private String originalCountry;

    @Column(name = "last_uploaded_movie")
    private LocalDate lastUploadedMovie;

    // __________________________________________________________

    @ManyToMany(mappedBy = "companies")
    private List<Movie> movies = new ArrayList<>();

    // __________________________________________________________

    public Company(CompanyTMDBDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.headquarters = dto.getHeadquarters();
        this.homepage = dto.getHomepage();
        this.logoPath = dto.getLogoPath();
        this.originalCountry = dto.getOriginCountry();
        this.parentCompany = dto.getParentCompany();
        this.movies = new ArrayList<>();
    }
    
}