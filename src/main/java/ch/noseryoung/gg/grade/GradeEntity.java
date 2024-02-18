package ch.noseryoung.gg.grade;

import ch.noseryoung.gg.klass.ClassEntity;
import ch.noseryoung.gg.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grade")
public class GradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private int gradeId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id")
    private ClassEntity classId;

    private float grade;
    private String comment;
    private String date;
}
