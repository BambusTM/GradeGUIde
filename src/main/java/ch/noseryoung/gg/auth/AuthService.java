package ch.noseryoung.gg.auth;


import ch.noseryoung.gg.config.JwtService;
import ch.noseryoung.gg.user.Role;
import ch.noseryoung.gg.user.UserRepository;
import ch.noseryoung.gg.user.UserEntity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) throws Exception {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new Exception("Username already exists");
        } else if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new Exception("Email already exists");
        }
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .build();
        userRepository.save(user);
        return getAuthResponse(user);
    }

    public AuthResponse authenticate(AuthRequest request) {
        return null;
    }

    private AuthResponse getAuthResponse(UserEntity user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole().toString());

        String jwtToken = JwtService.createToken(extraClaims, user);

        return AuthResponse.builder().accessToken(jwtToken).build();
    }
}
