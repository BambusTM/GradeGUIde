package ch.noseryoung.gg.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    boolean existsByUsername(String username);

  Optional<Object> findByEmail(String email);

  Optional<Object> findByUsername(String username);
}
