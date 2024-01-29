package ch.noseryoung.gg.grade;

import ch.noseryoung.gg.klass.ClassEntity;
import ch.noseryoung.gg.user.UserEntity;
import jakarta.persistence.*;

@Entity
public class GradeEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "grade")
    private float grade;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private UserEntity studentEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private ClassEntity class_id;
}
