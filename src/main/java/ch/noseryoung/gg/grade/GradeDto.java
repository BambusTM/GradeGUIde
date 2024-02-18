package ch.noseryoung.gg.grade;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GradeDto {
    private int classId;
    private int userId;
    private float grade;
    private String comment;
    private String date;

    @Setter
    @Getter
    @SuperBuilder
    @NoArgsConstructor
    public static class WithId extends GradeDto {
        protected int gradeId;
    }
}
