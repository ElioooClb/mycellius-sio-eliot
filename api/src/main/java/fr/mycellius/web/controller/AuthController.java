package fr.mycellius.web.controller;
import fr.mycellius.persistence.entity.UserEntity;
import fr.mycellius.persistence.jpa.UserJpaRepository;
import fr.mycellius.security.JwtService;
import fr.mycellius.web.dto.auth.LoginRequest;
import fr.mycellius.web.dto.auth.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserJpaRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;
    public AuthController(UserJpaRepository users, PasswordEncoder encoder, JwtService jwt) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (request == null || request.username() == null || request.password() == null) {
            return ResponseEntity.status(400).body("Missing credentials");
        }
        UserEntity user = users.findByUsername(request.username()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).body("Bad credentials");
        }
        if (!encoder.matches(request.password(), user.getPasswordHash())) {
            return ResponseEntity.status(401).body("Bad credentials");
        }
        String token = jwt.generateToken(user.getUsername(), user.getRole());
        return ResponseEntity.ok(new LoginResponse(token, user.getUsername(), user.getRole()));
    }
}