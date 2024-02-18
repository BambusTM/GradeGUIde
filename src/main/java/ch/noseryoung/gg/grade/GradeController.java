package ch.noseryoung.gg.grade;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grade")
@Tag(name = "grade")
public class GradeController {

        private final GradeService gradeService;

        @PostMapping
        public ResponseEntity<GradeDto.WithId> create(@RequestBody GradeDto gradeDto) {
            return gradeService.create(gradeDto);
        }

        @GetMapping("/{id}")
        public GradeDto.WithId getById(@PathVariable int id) {
            return gradeService.getById(id);
        }

        @GetMapping
        public List<GradeDto.WithId> getAllGrades() {
            return gradeService.getAllGrades();
        }

        @DeleteMapping("/{id}")
        public void deleteById(@PathVariable int id) {
            try {
                gradeService.deleteById(id);
            } catch (Exception e) {
                throw new RuntimeException("Could not delete grade");
            }
        }
}
