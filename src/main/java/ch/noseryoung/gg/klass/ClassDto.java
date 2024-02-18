package ch.noseryoung.gg.klass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ClassDto {
    private int userId;
    private String className;

    @Setter
    @Getter
    @SuperBuilder
    @NoArgsConstructor
    public static class WithId extends ClassDto {
        protected int classId;
    }
}
