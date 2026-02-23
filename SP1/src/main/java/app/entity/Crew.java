package app.entity;

import app.enums.CreditTitleEnum;
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

    // creditTitle -> Object of CreditTitleEnum. Use getDescription() to get description!
    // ___________________________
    //
    // Usage:
    // crew.getCreditTitle().getDescription()

    // Attributes
    @Id
    @Column(name = "id", unique = true)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "credit_title_enum", nullable = false)
    private CreditTitleEnum creditTitleEnum;

}