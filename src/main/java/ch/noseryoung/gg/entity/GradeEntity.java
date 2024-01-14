package ch.noseryoung.gg.entity;

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

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public UserEntity getStudentEntity() {
        return studentEntity;
    }

    public void setStudentEntity(UserEntity studentEntity) {
        this.studentEntity = studentEntity;
    }

    public ClassEntity getClass_id() {
        return class_id;
    }

    public void setClass_id(ClassEntity class_id) {
        this.class_id = class_id;
    }
}
