package ch.noseryoung.gg.klass;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/class")
@Tag(name = "Class")
public class ClassController {

    private final ClassService classService;

    @PostMapping()
    public ResponseEntity<ClassDto.WithId> create(@RequestBody ClassDto classDto) {
        return classService.create(classDto);
    }

    @GetMapping("/{id}")
    public ClassDto.WithId getById(@PathVariable int id) {
        return classService.getById(id);
    }

    @GetMapping("/all")
    public List<ClassDto.WithId> getAll() {
        return classService.getAllClasses();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassDto.WithId> updateById(@PathVariable int id, @RequestBody ClassDto classDto) {
        return classService.updateById(id, classDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        try {
            classService.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Could not delete class");
        }
    }
}
