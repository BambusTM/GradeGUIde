package ch.noseryoung.gg.repository;

import ch.noseryoung.gg.entity.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<GradeEntity, Integer> {
}
