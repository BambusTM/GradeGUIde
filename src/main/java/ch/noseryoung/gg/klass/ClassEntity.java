package ch.noseryoung.gg.klass;

import ch.noseryoung.gg.user.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "class")
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private int classId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    private String className;
}
