package ch.noseryoung.gg.grade;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<GradeEntity, Integer> {
    boolean existsByGradeId(int gradeId);
}
