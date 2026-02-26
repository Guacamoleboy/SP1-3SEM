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
@Table(name="genres")
public class Genre {

    // Attributes
    @Id
    @Column(name = "id", unique = true)
    private Integer id;

    @Column(name = "name", unique = true)
    private String genreName;

}