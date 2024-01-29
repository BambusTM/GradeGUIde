package ch.noseryoung.gg.grade;

public class GradeDto {
    float grade;
    String student_id;
    String class_id;

    public GradeDto(float grade, String student_id, String class_id) {
        this.grade = grade;
        this.student_id = student_id;
        this.class_id = class_id;
    }
}
