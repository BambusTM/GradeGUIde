package ch.noseryoung.gg.grade;

import ch.noseryoung.gg.klass.ClassEntity;
import ch.noseryoung.gg.klass.ClassRepository;
import ch.noseryoung.gg.user.UserEntity;
import ch.noseryoung.gg.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;

    public ResponseEntity<GradeDto.WithId> create(GradeDto gradeDto) {
        ClassEntity classEntity = classRepository.findById(gradeDto.getClassId())
                .orElseThrow(() -> new NotFoundException("Class not found"));

        UserEntity studentEntity = userRepository.findById(gradeDto.getStudentId())
                .orElseThrow(() -> new NotFoundException("Student not found"));

        UserEntity teacherEntity = userRepository.findById(gradeDto.getTeacherId())
                .orElseThrow(() -> new NotFoundException("Teacher not found"));


        GradeEntity newEntity = new GradeEntity();
        newEntity.setClassId(classEntity);
        newEntity.setStudentId(studentEntity);
        newEntity.setTeacherId(teacherEntity);
        newEntity.setGrade(gradeDto.getGrade());
        newEntity.setComment(gradeDto.getComment());
        newEntity.setDate(gradeDto.getDate());

        GradeEntity savedGradeEntity = gradeRepository.save(newEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(GradeDto.WithId.builder()
                .gradeId(savedGradeEntity.getGradeId())
                .classId(savedGradeEntity.getClassId().getClassId())
                .studentId(savedGradeEntity.getStudentId().getUserId())
                .teacherId(savedGradeEntity.getTeacherId().getUserId())
                .grade(savedGradeEntity.getGrade())
                .comment(savedGradeEntity.getComment())
                .date(savedGradeEntity.getDate())
                .build());
    }

    public GradeDto.WithId getMyGradeById(int id) {
        int currentUserId = getCurrentUserId();
        GradeEntity gradeEntity = gradeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grade not found"));

        if (gradeEntity.getStudentId().getUserId() != currentUserId) {
            throw new NotFoundException("You don't have a grade with this id");
        }
        return getGradeDtoWithId(gradeEntity);
    }

    public GradeDto.WithId getById(int id) {
        return gradeRepository.findById(id)
                .map(this::getGradeDtoWithId)
                .orElseThrow(() -> new NotFoundException("Grade not found"));
    }

    public List<GradeDto.WithId> getAllMyGrades() {
        int currentUserId = getCurrentUserId();
        List<GradeDto.WithId> gradeDtoList = gradeRepository.findAllByStudentIdUserId(currentUserId).stream()
                .map(gradeEntity -> getGradeDtoWithId((GradeEntity) gradeEntity))
                .collect(Collectors.toList());
        if (gradeDtoList.isEmpty()) {
            throw new NotFoundException("No grades found");
        }
        return gradeDtoList;
    }

    public List<GradeDto.WithId> getAllGrades() {
        List<GradeDto.WithId> gradeDtoList = gradeRepository.findAll().stream()
                .map(gradeEntity -> getGradeDtoWithId((GradeEntity) gradeEntity))
                .collect(Collectors.toList());
        if (gradeDtoList.isEmpty()) {
            throw new NotFoundException("No grades found");
        }
        return gradeDtoList;
    }

    public ResponseEntity<GradeDto.WithId> updateById(int id, GradeDto gradeDto) {
        GradeEntity gradeEntity = gradeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grade not found"));

        ClassEntity classEntity = classRepository.findById(gradeDto.getClassId())
                .orElseThrow(() -> new NotFoundException("Class not found"));

        UserEntity studentEntity = userRepository.findById(gradeDto.getStudentId())
                .orElseThrow(() -> new NotFoundException("Student not found"));

        UserEntity teacherEntity = userRepository.findById(gradeDto.getTeacherId())
                .orElseThrow(() -> new NotFoundException("Teacher not found"));

        gradeEntity.setClassId(classEntity);
        gradeEntity.setStudentId(studentEntity);
        gradeEntity.setTeacherId(teacherEntity);
        gradeEntity.setGrade(gradeDto.getGrade());
        gradeEntity.setComment(gradeDto.getComment());
        gradeEntity.setDate(gradeDto.getDate());

        GradeEntity updatedGradeEntity = gradeRepository.save(gradeEntity);

        return ResponseEntity.status(HttpStatus.OK).body(GradeDto.WithId.builder()
                .gradeId(updatedGradeEntity.getGradeId())
                .classId(updatedGradeEntity.getClassId().getClassId())
                .studentId(updatedGradeEntity.getStudentId().getUserId())
                .teacherId(updatedGradeEntity.getTeacherId().getUserId())
                .grade(updatedGradeEntity.getGrade())
                .comment(updatedGradeEntity.getComment())
                .date(updatedGradeEntity.getDate())
                .build());
    }

    public void deleteById(int id) {
        gradeRepository.deleteById(id);
    }

    private GradeDto.WithId getGradeDtoWithId(GradeEntity gradeEntity) {
        GradeDto.WithId gradeDto = new GradeDto.WithId();
        BeanUtils.copyProperties(gradeEntity, gradeDto);
        gradeDto.setStudentId(gradeEntity.getStudentId().getUserId());
        gradeDto.setTeacherId(gradeEntity.getTeacherId().getUserId());
        gradeDto.setClassId(gradeEntity.getClassId().getClassId());
        return gradeDto;
    }

    private int getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return user.getUserId();
    }
}
