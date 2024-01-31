package ch.noseryoung.gg.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
    String jwtToken = authService.register(request).getAccessToken();
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Access-Token " + jwtToken);
    return new ResponseEntity<>(headers, HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> authenticate(
      @RequestBody AuthRequest request) {
    AuthResponse authResponse = authService.authenticate(request);
    String jwtToken = authResponse.getAccessToken();
    String refreshToken = authResponse.getRefreshToken();
    HttpHeaders headers = new HttpHeaders();
    headers.add(
        "Authorization",
        "Access-Token " + jwtToken
    );
    headers.add(
        "Authorization",
        "Refresh-Token " + refreshToken
    );
    return new ResponseEntity<>(headers, HttpStatus.OK);
  }
}