package ch.noseryoung.gg.dto;

public class GradeDto {
    float grade;
    String student_id;
    String class_id;

    public GradeDto(float grade, String student_id, String class_id) {
        this.grade = grade;
        this.student_id = student_id;
        this.class_id = class_id;
    }

    // Getters and Setters

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }
}
