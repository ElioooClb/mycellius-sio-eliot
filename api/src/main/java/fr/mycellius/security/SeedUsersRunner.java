package fr.mycellius.security;
import fr.mycellius.persistence.entity.UserEntity;
import fr.mycellius.persistence.jpa.UserJpaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
@Component
public class SeedUsersRunner implements CommandLineRunner {
    private final boolean enabled;
    private final UserJpaRepository users;
    private final PasswordEncoder encoder;
    @Value("${mycellius.seed.admin.username:admin}") private String adminUsername;
    @Value("${mycellius.seed.admin.password:Admin123!}") private String adminPassword;
    @Value("${mycellius.seed.dev.username:dev}") private String devUsername;
    @Value("${mycellius.seed.dev.password:Dev123!}") private String devPassword;
    @Value("${mycellius.seed.stagiaire.username:stagiaire}") private String stagiaireUsername;
    @Value("${mycellius.seed.stagiaire.password:Stagiaire123!}") private String stagiairePassword;
    public SeedUsersRunner(
            @Value("${mycellius.seed.enabled:true}") boolean enabled,
            UserJpaRepository users,
            PasswordEncoder encoder
    ) {
        this.enabled = enabled;
        this.users = users;
        this.encoder = encoder;
    }
    @Override
    public void run(String... args) {
        if (!enabled) return;
        seedIfMissing(adminUsername, adminPassword, "ADMIN");
        seedIfMissing(devUsername, devPassword, "DEV");
        seedIfMissing(stagiaireUsername, stagiairePassword, "STAGIAIRE");
    }
    private void seedIfMissing(String username, String rawPassword, String role) {
        if (users.findByUsername(username).isPresent()) return;
        String hash = encoder.encode(rawPassword);
        users.save(new UserEntity(username, hash, role));
    }
}