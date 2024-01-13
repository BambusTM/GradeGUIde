package ch.noseryoung.gg.repository;

import ch.noseryoung.gg.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
