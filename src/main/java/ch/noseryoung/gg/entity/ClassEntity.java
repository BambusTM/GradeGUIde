package ch.noseryoung.gg.entity;

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

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public UserEntity getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(UserEntity teacher_id) {
        this.teacher_id = teacher_id;
    }
}
