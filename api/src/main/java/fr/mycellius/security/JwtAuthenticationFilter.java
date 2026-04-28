package fr.mycellius.security;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        if (uri.startsWith("/api/v1/pages")) {
            System.out.println("[JWT] " + request.getMethod() + " " + uri
                    + " authHeader=" + (header == null ? "null" : header.substring(0, Math.min(20, header.length())) + "..."));
        }

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                DecodedJWT jwt = jwtService.verify(token);
                String username = jwt.getSubject();
                String role = jwt.getClaim("role").asString();

                if (uri.startsWith("/api/v1/pages")) {
                    System.out.println("[JWT] OK user=" + username + " role=" + role);
                }

                var auth = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + role))
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ex) {
                System.err.println("[JWT] INVALID on " + uri + " -> " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
                ex.printStackTrace();
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
