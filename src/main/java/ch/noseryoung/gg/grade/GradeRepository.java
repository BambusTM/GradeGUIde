package ch.noseryoung.gg.grade;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<GradeEntity, Integer> {
    boolean existsByGradeId(int gradeId);

    List<GradeEntity> findAllByStudentIdUserId(int userId);
}
