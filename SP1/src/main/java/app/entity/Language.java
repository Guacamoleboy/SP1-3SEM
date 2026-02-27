package app.entity;

import app.enums.LanguageEnum;
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
@Table(name="languages")
public class Language {

    // _____________________________________________________________________________________________
    //
    // • LanguageEnum languageEnum
    //          - takes the first String in the enum
    //
    // _____________________________________________________________________________________________

    // Columns
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uuid", unique = true, nullable = false)
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(name = "language_enum", nullable = false)
    private LanguageEnum languageEnum;

    // _____________________________________________________________________________________________

}