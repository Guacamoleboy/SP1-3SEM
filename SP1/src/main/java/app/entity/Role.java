package app.entity;

import app.enums.RoleEnum;
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
@Table(name="roles")
public class Role {

    // _____________________________________________________________________________________________
    //
    //   • RoleEnum roleEnum takes the first String from the enum.
    //            Which would be ADMIN for example - and store it.
    //
    // _____________________________________________________________________________________________

    // Columns
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id", nullable = false, columnDefinition = "uuid")
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(name = "role_enum", nullable = false)
    private RoleEnum roleEnum;

    // _____________________________________________________________________________________________

}