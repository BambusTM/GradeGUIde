package ch.noseryoung.gg.grade;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grade")
@Tag(name = "Grade")
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    public ResponseEntity<GradeDto.WithId> create(@RequestBody GradeDto gradeDto) {
        return gradeService.create(gradeDto);
    }

    @GetMapping("/{id}")
    public GradeDto.WithId getById(@PathVariable int id) {
        if (isStudent(SecurityContextHolder.getContext().getAuthentication())) {
            return gradeService.getMyGradeById(id);
        } else if (isTeacher(SecurityContextHolder.getContext().getAuthentication())) {
            return gradeService.getById(id);
        }
        throw new RuntimeException("Unauthorized");
    }

    @GetMapping("/all")
    public List<GradeDto.WithId> getAllGrades() {
        if (isStudent(SecurityContextHolder.getContext().getAuthentication())) {
            return gradeService.getAllMyGrades();
        } else if (isTeacher(SecurityContextHolder.getContext().getAuthentication())) {
            return gradeService.getAllGrades();
        }
        return gradeService.getAllGrades();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeDto.WithId> updateById(@PathVariable int id, @RequestBody GradeDto gradeDto) {
        return gradeService.updateById(id, gradeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        try {
            gradeService.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Could not delete grade");
        }
    }

    private boolean isStudent(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("STUDENT"));
    }

    private boolean isTeacher(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("TEACHER"));
    }
}
