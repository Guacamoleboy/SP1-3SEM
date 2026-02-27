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

    // _____________________________________________________________________________________________
    //
    // • Apparently multiple companies own a Movie!
    //
    // • One (Company) One (User)
    //      - Unidirectioal
    //      - Company knows User. User doesn't know Company.
    //
    // • Many (Company) Many (Movie)
    //      - Bidirectional
    //      - Company knows Movie. Movie knows Company.
    //      - Mapped relation. Not a column in Company.
    //
    // _____________________________________________________________________________________________
    
    // Columns
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

    // _____________________________________________________________________________________________

    @ManyToMany(mappedBy = "companies")
    private List<Movie> movies = new ArrayList<>();

    // _____________________________________________________________________________________________
    // • Takes the ComapnyTMDBDTO and sends to Company entity.
    //      - Used for External API DTO Request (Inbound)

    public Company(CompanyTMDBDTO companyTMDBDTO) {
        this.id = companyTMDBDTO.getId();
        this.name = companyTMDBDTO.getName();
        this.description = companyTMDBDTO.getDescription();
        this.headquarters = companyTMDBDTO.getHeadquarters();
        this.homepage = companyTMDBDTO.getHomepage();
        this.logoPath = companyTMDBDTO.getLogoPath();
        this.originalCountry = companyTMDBDTO.getOriginCountry();
        this.parentCompany = companyTMDBDTO.getParentCompany();
        this.movies = new ArrayList<>();
    }

    // _____________________________________________________________________________________________
    
}