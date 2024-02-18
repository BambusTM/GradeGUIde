package ch.noseryoung.gg.klass;

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
public class ClassService {

    private final ClassRepository classRepository;
    private final UserRepository userRepository;

    public ResponseEntity<ClassDto.WithId> create(ClassDto classDto) {
        boolean isPresent = classRepository.existsByClassName(classDto.getClassName());
        if (isPresent) {
            return ResponseEntity.badRequest().build();
        }

        UserEntity userEntity = userRepository.findById(classDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        ClassEntity newEntity = new ClassEntity();
        newEntity.setClassName(classDto.getClassName());
        newEntity.setUserId(userEntity);

        ClassEntity savedClassEntity = classRepository.save(newEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(ClassDto.WithId.builder()
                .classId(savedClassEntity.getClassId())
                .userId(savedClassEntity.getUserId().getUserId())
                .className(savedClassEntity.getClassName())
                .build());
    }

    public ClassDto.WithId getById(int id) {
        try {
            return classRepository.findById(id).map(this::getClasses)
                    .orElseThrow(() -> new NotFoundException("Class not found"));
        } catch (Exception e) {
            throw new NotFoundException("Class not found");
        }
    }

    public List<ClassDto.WithId> getAllClasses() {
        List<ClassDto.WithId> classDtoList =
                classRepository.findAll().stream().map(this::getClasses)
                        .collect(Collectors.toList());
        if (classDtoList.isEmpty()) {
            throw new NotFoundException("No classes found");
        }
        return classDtoList;
    }

    public void deleteById(int id) {
        classRepository.deleteById(id);
    }

    private ClassDto.WithId getClasses(ClassEntity classEntity) {
        ClassDto.WithId classDto = new ClassDto.WithId();
        BeanUtils.copyProperties(classEntity, classDto);
        if (classEntity.getUserId() != null) {
            classDto.setUserId(classEntity.getUserId().getUserId());
        }
        return classDto;
    }
}
