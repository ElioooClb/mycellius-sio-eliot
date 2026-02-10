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
    private final long ttlSeconds;
    private final JWTVerifier verifier;
    public JwtService(
            @Value("${mycellius.jwt.secret}") String secret,
            @Value("${mycellius.jwt.issuer}") String issuer,
            @Value("${mycellius.jwt.ttlSeconds}") long ttlSeconds
    ) {
        this.algo = Algorithm.HMAC256(secret);
        this.issuer = issuer;
        this.ttlSeconds = ttlSeconds;
        this.verifier = JWT.require(algo).withIssuer(issuer).build();
    }
    public String generateToken(String username, String role) {
        Instant now = Instant.now();
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
}