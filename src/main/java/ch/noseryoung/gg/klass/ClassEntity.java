package ch.noseryoung.gg.klass;

import ch.noseryoung.gg.user.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "class")
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "class_name")
    private String class_name;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private UserEntity teacher_id;
}
