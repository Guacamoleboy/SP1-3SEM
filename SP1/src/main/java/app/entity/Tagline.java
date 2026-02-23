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
@Table(name="taglines")
public class Tagline {

    // Attributes
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "tag", nullable = false , unique = true)
    private String tag;

}