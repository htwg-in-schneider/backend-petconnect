package de.htwg.in.schneider.petconnect.backend.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configures our application with Spring Security to restrict access to our API
 * endpoints.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        return http
                .cors(withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/profile").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/ausschreibungen").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/api/ausschreibungen/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/api/ausschreibungen/*").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/users/*").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/users/me").authenticated()
                        .requestMatchers("/api/users/**").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/meldungen/*").authenticated()
                        .requestMatchers("/api/messages/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/anfragen").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/meldungen/*").authenticated()
                        .requestMatchers("/api/**").permitAll())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(withDefaults()))
                .build();
    }
}
