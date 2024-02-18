package ch.noseryoung.gg.klass;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/class")
@Tag(name = "Class")
public class ClassController {

    private final ClassService classService;

    @PostMapping()
    public ResponseEntity<ClassEntity> create(@RequestParam ClassDto classDto) {
        return classService.create(classDto);
    }
}
