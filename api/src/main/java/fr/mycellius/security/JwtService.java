package fr.mycellius.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final Algorithm algo;
    private final String issuer;
    private final long adminTtlSeconds;
    private final long devTtlSeconds;
    private final long stagiaireTtlSeconds;
    private final JWTVerifier verifier;

    public JwtService(
            @Value("${mycellius.jwt.secret}") String secret,
            @Value("${mycellius.jwt.issuer}") String issuer,
            @Value("${mycellius.jwt.ttlSeconds.admin}") long adminTtlSeconds,
            @Value("${mycellius.jwt.ttlSeconds.dev}") long devTtlSeconds,
            @Value("${mycellius.jwt.ttlSeconds.stagiaire}") long stagiaireTtlSeconds) {
        this.algo = Algorithm.HMAC256(secret);
        this.issuer = issuer;
        this.adminTtlSeconds = adminTtlSeconds;
        this.devTtlSeconds = devTtlSeconds;
        this.stagiaireTtlSeconds = stagiaireTtlSeconds;
        this.verifier = JWT.require(algo)
                .withIssuer(issuer)
                .build();
    }

    public String generateToken(String username, String role) {
        Instant now = Instant.now();
        long ttlSeconds = getTtlSecondsForRole(role);
        Instant exp = now.plusSeconds(ttlSeconds);

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(username)
                .withClaim("role", role)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(exp))
                .sign(algo);
    }

    public DecodedJWT verify(String token) {
        return verifier.verify(token);
    }

    private long getTtlSecondsForRole(String role) {
        return switch (role) {
            case "ADMIN" -> adminTtlSeconds;
            case "DEV" -> devTtlSeconds;
            case "STAGIAIRE" -> stagiaireTtlSeconds;
            default -> throw new IllegalArgumentException(
                    "Rôle inconnu : " + role);
        };
    }
}