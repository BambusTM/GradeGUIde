package ch.noseryoung.gg.auth;

import ch.noseryoung.gg.config.JwtService;
import ch.noseryoung.gg.user.Role;
import ch.noseryoung.gg.user.UserRepository;
import ch.noseryoung.gg.user.UserEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        var user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .build();
        userRepository.save(user);
        var jwtToken = JwtService.generateToken(user);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new NotFoundException("Invalid username or password");
        }
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));

        var jwtToken = JwtService.generateToken(user);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
