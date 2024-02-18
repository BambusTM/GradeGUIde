package ch.noseryoung.gg.grade;

import ch.noseryoung.gg.klass.ClassEntity;
import ch.noseryoung.gg.klass.ClassRepository;
import ch.noseryoung.gg.user.UserEntity;
import ch.noseryoung.gg.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;

    public ResponseEntity<GradeDto.WithId> create(GradeDto gradeDto) {
        ClassEntity classEntity = classRepository.findById(gradeDto.getClassId())
                .orElseThrow(() -> new NotFoundException("Class not found"));

        UserEntity userEntity = userRepository.findById(gradeDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        GradeEntity newEntity = new GradeEntity();
        newEntity.setClassId(classEntity);
        newEntity.setUserId(userEntity);
        newEntity.setGrade(gradeDto.getGrade());
        newEntity.setComment(gradeDto.getComment());
        newEntity.setDate(gradeDto.getDate());

        GradeEntity savedGradeEntity = gradeRepository.save(newEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(GradeDto.WithId.builder()
                .gradeId(savedGradeEntity.getGradeId())
                .classId(savedGradeEntity.getClassId().getClassId())
                .userId(savedGradeEntity.getUserId().getUserId())
                .grade(savedGradeEntity.getGrade())
                .comment(savedGradeEntity.getComment())
                .date(savedGradeEntity.getDate())
                .build());
    }

    public GradeDto.WithId getById(int id) {
        return gradeRepository.findById(id)
                .map(this::getGradeDtoWithId)
                .orElseThrow(() -> new NotFoundException("Grade not found"));
    }

    public List<GradeDto.WithId> getAllGrades() {
        List<GradeDto.WithId> gradeDtoList = gradeRepository.findAll().stream()
                .map(this::getGradeDtoWithId)
                .collect(Collectors.toList());
        if (gradeDtoList.isEmpty()) {
            throw new NotFoundException("No grades found");
        }
        return gradeDtoList;
    }

    public void deleteById(int id) {
        gradeRepository.deleteById(id);
    }

    private GradeDto.WithId getGradeDtoWithId(GradeEntity gradeEntity) {
        GradeDto.WithId gradeDto = new GradeDto.WithId();
        BeanUtils.copyProperties(gradeEntity, gradeDto);
        gradeDto.setUserId(gradeEntity.getUserId().getUserId());
        gradeDto.setClassId(gradeEntity.getClassId().getClassId());
        return gradeDto;
    }
}
