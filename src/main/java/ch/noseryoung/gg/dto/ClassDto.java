package ch.noseryoung.gg.dto;

public class ClassDto {
    String class_name;
    int teacher_Id;

    public ClassDto(String class_name, int teacher_Id) {
        this.class_name = class_name;
        this.teacher_Id = teacher_Id;
    }

    // Getters and Setters

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public int getTeacher_Id() {
        return teacher_Id;
    }

    public void setTeacher_Id(int teacher_Id) {
        this.teacher_Id = teacher_Id;
    }
}
