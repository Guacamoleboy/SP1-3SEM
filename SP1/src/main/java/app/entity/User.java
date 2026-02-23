package app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users", uniqueConstraints = @UniqueConstraint(columnNames = {"email", "username"}))
public class User {

    // Email + Username can't exist twice.
    //
    // One (Company) One (User) | Unidirectional -> No @mappedBy in User.java
    // Many (Users) One (Role) | Unidirectional -> No @mappedBy in Role.java

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false, unique = true)
    private Company company;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @ManyToOne
    @JoinColumn(name ="role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @Column(name = "email_hash", nullable = false, unique = true)
    private String emailHash;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name ="created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name="last_login", nullable = false)
    private LocalTime lastLogin;

    // __________________________________________________________

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        lastLogin = LocalTime.now();
    }

    // __________________________________________________________
    // Update

    @PreUpdate
    protected void onLogin() {
        lastLogin = LocalTime.now();
    }

}
