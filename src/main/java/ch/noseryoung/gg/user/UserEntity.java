package ch.noseryoung.gg.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"user\"")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", unique = true)
    @NotBlank(message = "need this")
    private String username;

    @Column(name = "password_hash")
    @NotBlank(message = "don't worry, it's save")
    private String password_hash;

    @Column(name = "email", unique = true)
    @NotBlank(message = "don't get lazy now")
    private String email;

    @Column(name = "role")
    private String role;
}
