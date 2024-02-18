package ch.noseryoung.gg.grade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeDto {
    private String userId;
    private String classId;
    private float grade;
    private String comment;
    private String date;
}
