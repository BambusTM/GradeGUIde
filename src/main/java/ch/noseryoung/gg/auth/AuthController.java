package ch.noseryoung.gg.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
    String jwtToken = authenticationService.register(request).getAccessToken();
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Access-Token " + jwtToken);
    return new ResponseEntity<>(headers, HttpStatus.CREATED);
  }

  @PostMapping("/register-admin")
  public ResponseEntity<AuthenticationResponse> registerAdmin(
      @RequestBody RegisterRequest request) {
    String jwtToken = authenticationService.registerAdmin(request).getAccessToken();
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Access-Token " + jwtToken);
    return new ResponseEntity<>(headers, HttpStatus.CREATED);
  }

  @PutMapping("/update-role/{username}")
  public ResponseEntity<AuthenticationResponse> updateRole(@PathVariable String username,
                                                           @RequestBody
                                                           UpdateRoleRequest updateRequest) {
    return ResponseEntity.ok(authenticationService.updateRole(username, updateRequest));
  }

  @PutMapping("/update-data/{username}")
  public ResponseEntity<AuthenticationResponse> updateData(@PathVariable String username,
                                                           @RequestBody
                                                           UpdateDataRequest updateRequest) {
    return ResponseEntity.ok(authenticationService.updateUserData(username, updateRequest));
  }

  @DeleteMapping("/delete/{username}")
  public void delete(@PathVariable String username) {
    authenticationService.deleteUser(username);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request) {
    AuthenticationResponse authResponse = authenticationService.authenticate(request);
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

  @PostMapping("/refresh-token")
  public ResponseEntity<String> refreshToken(
      HttpServletRequest request
  ) {
    AuthenticationResponse authResponse = authenticationService.refreshToken(
        request
    );

    HttpHeaders headers = new HttpHeaders();
    headers.set(
        "Authorization",
        "Access-Token " + authResponse.getAccessToken()
    );
    headers.add(
        "Authorization",
        "Refresh-Token "
            + authResponse.getRefreshToken()
    );

    return ResponseEntity.ok()
        .headers(headers)
        .build();
  }

  @PutMapping("/change-own-password")
  public ResponseEntity<?> changeOwnPassword(
      @RequestBody ChangePasswordRequest request,
      Principal connectedUser
  ) {
    authenticationService.changeOwnPassword(request, connectedUser);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/change-password/{username}")
  public ResponseEntity<?> changePassword(@PathVariable String username,
                                          @RequestBody ChangePasswordRequest request,
                                          Principal connectedUser
  ) {
    authenticationService.changePassword(request, connectedUser, username);
    return ResponseEntity.ok().build();
  }
}