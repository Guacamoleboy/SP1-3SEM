package app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users", uniqueConstraints = @UniqueConstraint(columnNames = {"email_hash", "username"}))
public class User {

    // _____________________________________________________________________________________________
    //
    // •  Email + Username can't exist twice.
    //
    // •  One (Company) One (User)
    //              - Unidirectional
    //              - No @mappedBy in User.java
    //
    // •  Many (Users) One (Role)
    //              - Unidirectional
    //              - No @mappedBy in Role.java
    //              - FK Column
    //              - "role_id" is "roles.id"
    //
    // _____________________________________________________________________________________________

    // Columns
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "email_hash", nullable = false, unique = true)
    private String emailHash;
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    @Column(name ="created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @Column(name="last_login", nullable = false)
    private LocalDateTime lastLogin;
    @ManyToOne
    @JoinColumn(name ="role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    // _____________________________________________________________________________________________

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        lastLogin = LocalDateTime.now();
    }

    // _____________________________________________________________________________________________

    @PreUpdate
    protected void onLogin() {
        lastLogin = LocalDateTime.now();
    }

}
