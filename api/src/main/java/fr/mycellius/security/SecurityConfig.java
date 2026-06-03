package fr.mycellius.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

        @Value("${mycellius.cors.allowed-origins}")
        private String allowedOrigins;

        @Bean
        public SecurityFilterChain securityFilterChain(
                        HttpSecurity http,
                        JwtService jwtService) throws Exception {

                http
                                .csrf(csrf -> csrf.disable())
                                .cors(Customizer.withDefaults())
                                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .httpBasic(b -> b.disable())
                                .formLogin(f -> f.disable())
                                .authorizeHttpRequests(auth -> auth

                                                // Requêtes CORS
                                                .requestMatchers(HttpMethod.OPTIONS, "/**")
                                                .permitAll()

                                                // Routes publiques
                                                .requestMatchers("/error")
                                                .permitAll()

                                                .requestMatchers("/api/v1/auth/login")
                                                .permitAll()

                                                .requestMatchers("/api/health", "/actuator/health")
                                                .permitAll()

                                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                                                .permitAll()

                                                // Lecture des pages
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/v1/pages",
                                                                "/api/v1/pages/**")
                                                .hasAnyRole("STAGIAIRE", "DEV", "ADMIN")

                                                // Création des pages
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/v1/pages",
                                                                "/api/v1/pages/**")
                                                .hasAnyRole("DEV", "ADMIN")

                                                // Suppression : ADMIN uniquement
                                                .requestMatchers(
                                                                HttpMethod.DELETE,
                                                                "/api/v1/pages/**")
                                                .hasRole("ADMIN")

                                                // Modification des pages
                                                .requestMatchers(
                                                                HttpMethod.PUT,
                                                                "/api/v1/pages",
                                                                "/api/v1/pages/**")
                                                .hasAnyRole("DEV", "ADMIN")

                                                .anyRequest()
                                                .authenticated())
                                .addFilterBefore(
                                                new JwtAuthenticationFilter(jwtService),
                                                UsernamePasswordAuthenticationFilter.class);

                http.exceptionHandling(eh -> eh
                                .authenticationEntryPoint(
                                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                                .accessDeniedHandler(
                                                (req, res, ex) -> res.sendError(HttpStatus.FORBIDDEN.value())));

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {

                List<String> origins = Arrays.stream(allowedOrigins.split(","))
                                .map(String::trim)
                                .filter(s -> !s.isEmpty())
                                .toList();

                CorsConfiguration cfg = new CorsConfiguration();

                cfg.setAllowedOrigins(origins);
                cfg.setAllowedMethods(
                                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                cfg.setAllowedHeaders(
                                List.of("Authorization", "Content-Type"));
                cfg.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

                source.registerCorsConfiguration("/**", cfg);

                return source;
        }
}