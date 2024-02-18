package ch.noseryoung.gg.klass;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassService {

    private final ClassRepository classRepository;

    public ResponseEntity<ClassEntity> create(ClassDto classDto) {
        ClassEntity classEntity = new ClassEntity();

        boolean isPresent = classRepository.findAll().stream()
                .anyMatch(classEntity1 -> classEntity1.getClassName().equals(classDto.getClassName()));
        if (isPresent) {
            return ResponseEntity.badRequest().build();
        }

        classEntity.setClassName(classDto.getClassName());
        ClassEntity savedClassEntity = classRepository.save(classEntity);

        return ResponseEntity.ok(savedClassEntity);
    }
}
