package cms_back.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import cms_back.config.jwt.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private static final List<String> DEFAULT_DEV_ORIGINS = List.of(
            "http://localhost:5173",
            "http://localhost:4173",
            "http://127.0.0.1:5173"
    );

    private final List<String> allowedOrigins;

    public SecurityConfig(@Value("${app.cors.allowed-origins:}") String allowedOriginsCsv) {
        // 운영: app.cors.allowed-origins 콤마 구분 값으로 도메인/IP 추가.
        // 비워두면 개발용 localhost 출처만 허용한다(동일 출처 Nginx 프록시 환경에서는 CORS 자체 불필요).
        List<String> origins = new ArrayList<>(DEFAULT_DEV_ORIGINS);
        if (allowedOriginsCsv != null && !allowedOriginsCsv.isBlank()) {
            Arrays.stream(allowedOriginsCsv.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .forEach(origins::add);
        }
        this.allowedOrigins = List.copyOf(origins);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        // 헬스체크: systemd/ALB/업타임 모니터가 호출. UP/DOWN status만 노출(show-details=never).
                        .requestMatchers("/actuator/health").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
