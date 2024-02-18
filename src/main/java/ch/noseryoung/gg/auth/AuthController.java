package ch.noseryoung.gg.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register/student")
  public ResponseEntity<AuthResponse> registerStudent(@RequestBody RegisterRequest request) {

    return ResponseEntity.ok(authService.registerStudent(request));
  }

  @PostMapping("/register/teacher")
  public ResponseEntity<AuthResponse> registerTeacher(@RequestBody RegisterRequest request) {

    return ResponseEntity.ok(authService.registerTeacher(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {

    return ResponseEntity.ok(authService.authenticate(request));
  }
}